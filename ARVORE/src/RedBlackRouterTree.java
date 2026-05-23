

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
}
