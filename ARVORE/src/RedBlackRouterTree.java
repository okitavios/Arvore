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

    // Nó sentinela: representa todas as folhas nulas (sempre BLACK)
    private final Node NIL = new Node(null);

    private Node root;
    private int contadorRotacoes = 0;

    public RedBlackRouterTree() {
        NIL.color = BLACK;
        NIL.left = NIL;
        NIL.right = NIL;
        NIL.parent = NIL;
        root = NIL;
    }

    public int getContadorRotacoes() {
        return contadorRotacoes;
    }

    private void rotateLeft(Node x) {
        contadorRotacoes++;
        Node y = x.right;
        x.right = y.left;
        if (y.left != NIL) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == NIL)       root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else                         x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node y) {
        contadorRotacoes++;
        Node x = y.left;
        y.left = x.right;
        if (x.right != NIL) x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == NIL)       root = x;
        else if (y == y.parent.right) y.parent.right = x;
        else                          y.parent.left = x;
        x.right = y;
        y.parent = x;
    }

    public void insert(PacketRule data) {
        Node node = new Node(data);
        node.left = NIL;
        node.right = NIL;
        node.color = RED;

        Node parent = NIL;
        Node current = root;

        while (current != NIL) {
            parent = current;
            if (data.compareTo(current.data) < 0) current = current.left;
            else                                   current = current.right;
        }

        node.parent = parent;
        if (parent == NIL)                        root = node;
        else if (data.compareTo(parent.data) < 0) parent.left = node;
        else                                       parent.right = node;

        fixInsert(node);
    }

    private void fixInsert(Node z) {
        while (z.parent.color == RED) {
            Node gp = z.parent.parent;
            if (z.parent == gp.left) {
                Node y = gp.right;
                if (y.color == RED) {           // Caso 1
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    gp.color = RED;
                    z = gp;
                } else {
                    if (z == z.parent.right) {  // Caso 2
                        z = z.parent;
                        rotateLeft(z);
                    }
                    z.parent.color = BLACK;     // Caso 3
                    gp.color = RED;
                    rotateRight(gp);
                }
            } else {
                Node y = gp.left;
                if (y.color == RED) {
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
        while (current != NIL) {
            int cmp = key.compareTo(current.data);
            if (cmp == 0)    return current.data;
            current = (cmp < 0) ? current.left : current.right;
        }
        return null;
    }

    public void remove(PacketRule key) {
        Node z = root;
        while (z != NIL) {
            int cmp = key.compareTo(z.data);
            if (cmp == 0) break;
            z = (cmp < 0) ? z.left : z.right;
        }
        if (z == NIL) return;
        deleteNode(z);
    }

    private void deleteNode(Node z) {
        Node y = z;
        boolean yOriginalColor = y.color;
        Node x;

        if (z.left == NIL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;

            if (y.parent == z) {
                x.parent = y;               // funciona mesmo se x == NIL
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
            fixDelete(x);                   // x nunca é null; pode ser NIL
    }

    private void transplant(Node u, Node v) {
        if (u.parent == NIL)       root = v;
        else if (u == u.parent.left) u.parent.left = v;
        else                         u.parent.right = v;
        v.parent = u.parent;        // seguro: v pode ser NIL, NIL.parent é válido
    }

    private Node minimum(Node node) {
        while (node.left != NIL)
            node = node.left;
        return node;
    }

    private void fixDelete(Node x) {
        while (x != root && x.color == BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == RED) {                           // Caso 1
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == BLACK && w.right.color == BLACK) { // Caso 2
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.right.color == BLACK) {               // Caso 3
                        w.left.color = BLACK;
                        w.color = RED;
                        rotateRight(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;                   // Caso 4
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == BLACK && w.left.color == BLACK) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.left.color == BLACK) {
                        w.right.color = BLACK;
                        w.color = RED;
                        rotateLeft(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }
}