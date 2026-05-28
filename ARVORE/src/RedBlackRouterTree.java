//correções: 
//linha 240 - adicionar x = y.right;
//linha 280 - adicionar if (node == null) return null;

public class RedBlackRouterTree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {

        PacketRule data;
        Node left, right, parent;
        boolean color;

        Node(PacketRule data) {
            this.data = data;
            this.color = RED;
        }
    }

    private Node root;

    private int contadorRotacoes = 0;

    public int getContadorRotacoes() {
        return contadorRotacoes;
    }

    private void rotateLeft(Node x) {

        contadorRotacoes++;

        Node y = x.right;

        x.right = y.left;

        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;

        else if (x == x.parent.left)
            x.parent.left = y;

        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node y) {

        contadorRotacoes++;

        Node x = y.left;

        y.left = x.right;

        if (x.right != null)
            x.right.parent = y;

        x.parent = y.parent;

        if (y.parent == null)
            root = x;

        else if (y == y.parent.right)
            y.parent.right = x;

        else
            y.parent.left = x;

        x.right = y;
        y.parent = x;
    }

    public void insert(PacketRule data) {

        Node node = new Node(data);

        Node parent = null;
        Node current = root;

        while (current != null) {

            parent = current;

            if (data.compareTo(current.data) < 0)
                current = current.left;

            else
                current = current.right;
        }

        node.parent = parent;

        if (parent == null)
            root = node;

        else if (data.compareTo(parent.data) < 0)
            parent.left = node;

        else
            parent.right = node;

        fixInsert(node);
    }

    private void fixInsert(Node z) {

        while (z.parent != null && z.parent.color == RED) {

            Node gp = z.parent.parent;

            if (z.parent == gp.left) {

                Node y = gp.right;

                if (y != null && y.color == RED) {

                    z.parent.color = BLACK;
                    y.color = BLACK;
                    gp.color = RED;
                    z = gp;

                } else {

                    if (z == z.parent.right) {
                        z = z.parent;
                        rotateLeft(z);
                    }

                    z.parent.color = BLACK;
                    gp.color = RED;

                    rotateRight(gp);
                }

            } else {

                Node y = gp.left;

                if (y != null && y.color == RED) {

                    z.parent.color = BLACK;
                    y.color = BLACK;
                    gp.color = RED;

                    z = gp;

                } else {

                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }

                    z.parent.color = BLACK;
                    gp.color = RED;

                    rotateLeft(gp);
                }
            }
        }

        root.color = BLACK;
    }

    public PacketRule search(PacketRule key) {

        Node current = root;

        while (current != null) {

            int cmp = key.compareTo(current.data);

            if (cmp == 0)
                return current.data;

            current = (cmp < 0) ? current.left : current.right;
        }

        return null;
    }

    // =========================
    // REMOÇÃO
    // =========================

    public void remove(PacketRule key) {

        Node z = root;

        while (z != null) {

            int cmp = key.compareTo(z.data);

            if (cmp == 0)
                break;

            z = (cmp < 0) ? z.left : z.right;
        }

        if (z == null)
            return;

        deleteNode(z);
    }

    private void deleteNode(Node z) {

        Node y = z;
        boolean yOriginalColor = y.color;

        Node x;

        if (z.left == null) {

            x = z.right;
            transplant(z, z.right);

        } else if (z.right == null) {

            x = z.left;
            transplant(z, z.left);

        } else {

            y = minimum(z.right);
            yOriginalColor = y.color;

            x = y.right;

            if (y.parent == z) {

                if (x != null)
                    x.parent = y;

            } else {

                transplant(y, y.right);
                x = y.right;
                y.right = z.right;
                y.right.parent = y;
            }

            transplant(z, y);

            y.left = z.left;
            y.left.parent = y;

            y.color = z.color;
        }

        if (yOriginalColor == BLACK)
            fixDelete(x);
    }

    private void transplant(Node u, Node v) {

        if (u.parent == null)
            root = v;

        else if (u == u.parent.left)
            u.parent.left = v;

        else
            u.parent.right = v;

        if (v != null)
            v.parent = u.parent;
    }

    private Node minimum(Node node) {
        if (node == null) return null;
        while (node.left != null)
            node = node.left;

        return node;
    }

    private void fixDelete(Node x) {

        while (x != root && getColor(x) == BLACK) {

            if (x == x.parent.left) {

                Node w = x.parent.right;

                if (getColor(w) == RED) {

                    w.color = BLACK;
                    x.parent.color = RED;

                    rotateLeft(x.parent);

                    w = x.parent.right;
                }

                if (getColor(w.left) == BLACK &&
                        getColor(w.right) == BLACK) {

                    w.color = RED;
                    x = x.parent;

                } else {

                    if (getColor(w.right) == BLACK) {

                        if (w.left != null)
                            w.left.color = BLACK;

                        w.color = RED;

                        rotateRight(w);

                        w = x.parent.right;
                    }

                    w.color = x.parent.color;
                    x.parent.color = BLACK;

                    if (w.right != null)
                        w.right.color = BLACK;

                    rotateLeft(x.parent);

                    x = root;
                }

            } else {

                Node w = x.parent.left;

                if (getColor(w) == RED) {

                    w.color = BLACK;
                    x.parent.color = RED;

                    rotateRight(x.parent);

                    w = x.parent.left;
                }

                if (getColor(w.right) == BLACK &&
                        getColor(w.left) == BLACK) {

                    w.color = RED;
                    x = x.parent;

                } else {

                    if (getColor(w.left) == BLACK) {

                        if (w.right != null)
                            w.right.color = BLACK;

                        w.color = RED;

                        rotateLeft(w);

                        w = x.parent.left;
                    }

                    w.color = x.parent.color;
                    x.parent.color = BLACK;

                    if (w.left != null)
                        w.left.color = BLACK;

                    rotateRight(x.parent);

                    x = root;
                }
            }
        }

        if (x != null)
            x.color = BLACK;
    }

    private boolean getColor(Node node) {
        return node == null ? BLACK : node.color ;
    }
}
