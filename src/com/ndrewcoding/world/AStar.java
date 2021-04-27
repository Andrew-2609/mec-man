package com.ndrewcoding.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class AStar {

    public static double lastTime = System.currentTimeMillis();
    private static final Comparator<Node> NODE_SORTER = Comparator.comparingDouble(n0 -> n0.fCost);

    public static List<Node> findPath(Vector2i start, Vector2i end) {
        lastTime = System.currentTimeMillis();
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();

        Node current = new Node(start, null, 0, getDistance(start, end));
        openList.add(current);
        while (openList.size() > 0) {
            openList.sort(NODE_SORTER);
            current = openList.get(0);
            if (current.tile.equals(end)) {
                List<Node> path = new ArrayList<>();
                while (current.parent != null) {
                    path.add(current);
                    current = current.parent;
                }
                openList.clear();
                closedList.clear();
                return path;
            }

            openList.remove(current);
            closedList.add(current);

            for (int i = 0; i < 9; i++) {
                if (i == 4) continue;
                int x = current.tile.x;
                int y = current.tile.y;
                int xi = (i % 3) - 1;
                int yi = (i / 3) - 1;
                Tile tile = World.tiles[x + xi + ((y + yi) * World.WIDTH)];
                if (tile == null) continue;
                if (tile instanceof WallTile) continue;
                if (i == 0) {
                    Tile test = World.tiles[x + xi + 1 + ((y + yi) * World.WIDTH)];
                    Tile test2 = World.tiles[x + xi + ((y + yi + 1) * World.WIDTH)];
                    if (test instanceof WallTile || test2 instanceof WallTile) {
                        continue;
                    }
                } else if (i == 2) {
                    Tile test = World.tiles[x + xi - 1 + ((y + yi) * World.WIDTH)];
                    Tile test2 = World.tiles[x + xi + ((y + yi + 1) * World.WIDTH)];
                    if (test instanceof WallTile || test2 instanceof WallTile) {
                        continue;
                    }
                } else if (i == 6) {
                    Tile test = World.tiles[x + xi + ((y + yi - 1) * World.WIDTH)];
                    Tile test2 = World.tiles[x + xi + 1 + ((y + yi) * World.WIDTH)];
                    if (test instanceof WallTile || test2 instanceof WallTile) {
                        continue;
                    }
                } else if (i == 8) {
                    Tile test = World.tiles[x + xi + ((y + yi - 1) * World.WIDTH)];
                    Tile test2 = World.tiles[x + xi - 1 + ((y + yi) * World.WIDTH)];
                    if (test instanceof WallTile || test2 instanceof WallTile) {
                        continue;
                    }
                }

                Vector2i a = new Vector2i(x + xi, y + yi);
                double gCost = current.gCost + getDistance(current.tile, a);
                double hCost = getDistance(a, end);

                Node node = new Node(a, current, gCost, hCost);

                if (vecInList(closedList, a) && gCost >= current.gCost) continue;

                if (!vecInList(openList, a)) {
                    openList.add(node);
                } else if (gCost < current.gCost) {
                    openList.remove(current);
                    openList.add(node);
                }

            }
        }
        closedList.clear();
        return null;
    }

    private static boolean vecInList(List<Node> list, Vector2i vector) {
        for (Node node : list) {
            if (node.tile.equals(vector)) {
                return true;
            }
        }
        return false;
    }

    private static double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.x - goal.x;
        double dy = tile.y - goal.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

}
