package de.erdbeerbaerlp.bm_mf;

import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector2i;
import com.technicjelle.BMUtils.Cheese;
import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.markers.ExtrudeMarker;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.ShapeMarker;
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Shape;
import de.erdbeerbaerlp.bm_mf.accessors.FrontierDataAccessor;
import games.alejandrocoria.mapfrontiers.common.FrontierData;
import games.alejandrocoria.mapfrontiers.common.FrontiersManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.lang.module.Configuration;
import java.util.*;
import java.util.stream.Collectors;

public class FrontierTask extends TimerTask {
    @Override
    public void run() {
        if (!BlueMapAPI.getInstance().isPresent()) return;
        BlueMapAPI.getInstance().get().getMaps().iterator().forEachRemaining((map) -> {
            final MarkerSet markerSet = new MarkerSet(Config.instance().general.markerSet);
            final Iterator<ResourceKey<Level>> levels = BluemapFrontiers.minecraftServer.levelKeys().iterator();

            final List<FrontierData> frontiers = new ArrayList<>();
            while (levels.hasNext()) {
                final ResourceKey<Level> level = levels.next();
                if (level.location().toString().equals(map.getWorld().getId().split("#")[1])) {
                    frontiers.addAll(FrontiersManager.instance.getAllGlobalFrontiers(level));
                    break;
                }
            }
            frontiers.forEach((f) -> {
                FrontierDataAccessor fda = (FrontierDataAccessor) f;
                if (!f.getVisibility(FrontierData.VisibilityData.Visibility.Fullscreen)) return;
                if (f.getMode().equals(FrontierData.Mode.Vertex)) {
                    final List<BlockPos> verts = fda.bmmf$getVertices();
                    final Shape.Builder shape = new Shape.Builder();
                    int count = 0;
                    for (final BlockPos v : verts) {
                        shape.addPoint(Vector2d.from(v.get(Direction.Axis.X), v.get(Direction.Axis.Z)));
                        count++;
                    }

                    if (count < 3) return;
                    final Shape s = shape.build();

                    if(Config.instance().general.renderType == RenderType.FLAT) {
                        final ShapeMarker.Builder b = new ShapeMarker.Builder();
                        b.label(f.getName1() + " " + f.getName2());
                        if (s.getPointCount() < 3) return;
                        b.shape(s, Config.instance().flatMode.yLevel);
                        b.fillColor(new Color(f.getColor(), Config.instance().general.infillAlpha));
                        b.lineColor(new Color(f.getColor(), Config.instance().general.lineAlpha));
                        b.depthTestEnabled(false);
                        markerSet.put(f.getId().toString(), b.build());
                    }else if(Config.instance().general.renderType == RenderType.VOLUME){
                        final ExtrudeMarker.Builder b = new ExtrudeMarker.Builder();
                        b.label(f.getName1() + " " + f.getName2());
                        if (s.getPointCount() < 3) return;
                        b.shape(s, Config.instance().volumeMode.minY, Config.instance().volumeMode.maxY);
                        b.fillColor(new Color(f.getColor(), Config.instance().general.infillAlpha));
                        b.lineColor(new Color(f.getColor(), Config.instance().general.lineAlpha));
                        b.depthTestEnabled(false);
                        markerSet.put(f.getId().toString(), b.build());
                    }
                }
                if (f.getMode().equals(FrontierData.Mode.Chunk)) {
                    final Set<ChunkPos> chunks = fda.bmmf$getChunks();

                    final Cheese[] platter = Cheese.createPlatterFromChunks(chunks.stream()
                            .map(chunk -> new Vector2i(chunk.x, chunk.z)).toArray(Vector2i[]::new)).toArray(new Cheese[0]);
                    for (int i = 0; i < platter.length; i++) {
                        Cheese cheese = platter[i];

                        if(Config.instance().general.renderType == RenderType.FLAT) {
                            final ShapeMarker chunkMarker = new ShapeMarker.Builder()
                                    .label(f.getName1() + " " + f.getName2())
                                    .shape(cheese.getShape(), Config.instance().flatMode.yLevel)
                                    .holes(cheese.getHoles().toArray(new Shape[0]))
                                    .fillColor(new Color(f.getColor(), Config.instance().general.infillAlpha))
                                    .lineColor(new Color(f.getColor(), Config.instance().general.lineAlpha))
                                    .depthTestEnabled(!Config.instance().general.alwaysOnTop)
                                    .build();
                            markerSet.put(f.getId().toString() + "-segment-" + i, chunkMarker);
                        }else if(Config.instance().general.renderType == RenderType.VOLUME){
                            final ExtrudeMarker chunkMarker = new ExtrudeMarker.Builder()
                                    .label(f.getName1() + " " + f.getName2())
                                    .shape(cheese.getShape(), Config.instance().volumeMode.minY, Config.instance().volumeMode.maxY)
                                    .holes(cheese.getHoles().toArray(new Shape[0]))
                                    .fillColor(new Color(f.getColor(), Config.instance().general.infillAlpha))
                                    .lineColor(new Color(f.getColor(), Config.instance().general.lineAlpha))
                                    .depthTestEnabled(!Config.instance().general.alwaysOnTop)
                                    .build();
                            markerSet.put(f.getId().toString() + "-segment-" + i, chunkMarker);
                        }
                    }
                }
            });
            map.getMarkerSets().put("mapfrontiers", markerSet);
        });
    }

}
