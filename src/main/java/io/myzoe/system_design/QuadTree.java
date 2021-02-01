package io.myzoe.system_design;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class QuadTree {
    private int size;
    private final int capacity;
    private QuadTreeNode root;
    public final long WIDTH;
    public final long HEIGHT;


    public QuadTree(int capacity, long xleft, long ybottom, long xright, long ytop) {
        this.size = 0;
        this.capacity = capacity;
        WIDTH = xright - xleft;
        HEIGHT = ytop - ybottom;
        this.root = new QuadTreeNode(xleft, ybottom, xright, ytop);

    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean put(SpatialObject obj) {
        boolean res = this.root.put(obj);
        if (res) {
            this.size++;
        }
        return res;
    }

    public boolean remove(SpatialObject obj) {
        boolean res = this.root.remove(obj);
        if (res) {
            this.size--;
        }
        return res;
    }

    public List<SpatialObject> getAll() {
        List<SpatialObject> res = new ArrayList<>();
        this.root.getAll(res);
        return res;
    }

    public List<SpatialObject> getIntersect(double x0, double y0, double x1, double y1) {
        List<SpatialObject> res = new ArrayList<>();
        this.root.getIntersect(res, x0, y0, x1, y1);
        return res;
    }

    private class QuadTreeNode {
        private long xright;
        private long ytop;
        private long xleft;
        private long ybottom;
        private boolean leafnode;

        private ConcurrentHashMap<SpatialObject, Integer> locations;

        private QuadTreeNode nw;
        private QuadTreeNode ne;
        private QuadTreeNode sw;
        private QuadTreeNode se;

        public QuadTreeNode(long xleft, long ybottom, long xright, long ytop) {
            this.xright = xright;
            this.ytop = ytop;
            this.xleft = xleft;
            this.ybottom = ybottom;
            this.locations = new ConcurrentHashMap<>();
            this.leafnode = true;
        }


        public long[] getFrame() {
            return new long[]{xleft,ybottom, xright,ytop};
        }

        public long getXright() {
            return xright;
        }

        public long getYtop() {
            return ytop;
        }

        public long getXleft() {
            return xleft;
        }

        public long getYbottom() {
            return ybottom;
        }

        public boolean isLeafnode() {
            return leafnode;
        }

        public boolean put(SpatialObject obj) {
            if (obj.getLongitude() >= (double)xright ||
                    obj.getLongitude() < (double)xleft ||
                    obj.getLatitude() >= (double)ytop ||
                    obj.getLatitude() < (double)ybottom) {
                return false;
            }

            if (QuadTree.this.capacity == this.locations.size()) {
                if (this.leafnode) {
                    this.createChildren();
                }
                return nw.put(obj) || ne.put(obj) || sw.put(obj) || se.put(obj);
            }

            return this.locations.put(obj, 0) == null;
        }

        public boolean remove(SpatialObject obj) {
            if (obj.getLongitude() >= (double)xright ||
                    obj.getLongitude() < (double)xleft ||
                    obj.getLatitude() >= (double)ytop ||
                    obj.getLatitude() < (double)ybottom) {
                return false;
            }

            if (this.locations.remove(obj) != null) {
                return true;
            }

            if (this.leafnode) {
                return false;
            }

            return nw.remove(obj) || ne.remove(obj) || sw.remove(obj) || se.remove(obj);

        }

        public void getAll(List<SpatialObject> res) {
            res.addAll(this.locations.keySet());
            if (!this.leafnode) {
                this.nw.getAll(res);
                this.ne.getAll(res);
                this.sw.getAll(res);
                this.se.getAll(res);
            }
        }

        private void createChildren() {
            this.leafnode = false;
            long midX = (xleft + xright) >> 1;
            long midY = (ybottom + ytop) >> 1;
            this.nw = new QuadTreeNode(xleft, ybottom, midX, midY);
            this.ne = new QuadTreeNode(midX, ybottom, xright, midY);
            this.sw = new QuadTreeNode(xleft, midY, midX, ytop);
            this.se = new QuadTreeNode(midX, midY, xright, ytop);
        }

        public void getIntersect(List<SpatialObject> res, double x0, double y0, double x1, double y1) {
            if (intersect(x0, y0, x1, y1)) {
                this.locations.forEach((loc,val) -> {
                    if (loc.intersect(x0, y0, x1, y1)) {
                        res.add(loc);
                    }
                });

                if (this.leafnode) {
                    return;
                }

                nw.getIntersect(res, x0, y0, x1, y1);
                ne.getIntersect(res, x0, y0, x1, y1);
                sw.getIntersect(res, x0, y0, x1, y1);
                se.getIntersect(res, x0, y0, x1, y1);
            }
        }

        private boolean intersect(double x0, double y0, double x1, double y1) {
            return this.xleft < x1 && x0 < this.xright && this.ybottom < y1 && y0 < this.ytop;
        }
    }
}
