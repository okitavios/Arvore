

public class AVLRouterTree {

    private class Node {

        PacketRule data;
        Node left, right;
        int height;

        Node(PacketRule data) {
            this.data = data;
            this.height = 1;
        }
    }

    private Node root;

    private int contadorRotacoes = 0;

    public int getContadorRotacoes() {
        return contadorRotacoes;
    }

    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    private int balance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    private Node rotateRight(Node y) {

        contadorRotacoes++;

        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    private Node rotateLeft(Node x) {

        contadorRotacoes++;

        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    public void insert(PacketRule data) {
        root = insert(root, data);
    }

    private Node insert(Node node, PacketRule data) {

        if (node == null)
            return new Node(data);

        if (data.compareTo(node.data) < 0)
            node.left = insert(node.left, data);

        else if (data.compareTo(node.data) > 0)
            node.right = insert(node.right, data);

        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balance(node);

        // Left Left
        if (balance > 1 && data.compareTo(node.left.data) < 0)
            return rotateRight(node);

        // Right Right
        if (balance < -1 && data.compareTo(node.right.data) > 0)
            return rotateLeft(node);

        // Left Right
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Left
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
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

    public void delete(PacketRule key) {
        root = delete(root, key);
    }

    private Node delete(Node node, PacketRule key) {

        if (node == null)
            return null;

        if (key.compareTo(node.data) < 0)
            node.left = delete(node.left, key);

        else if (key.compareTo(node.data) > 0)
            node.right = delete(node.right, key);

        else {

            if (node.left == null || node.right == null) {

                node = (node.left != null) ? node.left : node.right;

            } else {

                Node temp = min(node.right);

                node.data = temp.data;

                node.right = delete(node.right, temp.data);
            }
        }

        if (node == null)
            return null;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balance(node);

        // Left Left
        if (balance > 1 && balance(node.left) >= 0)
            return rotateRight(node);

        // Left Right
        if (balance > 1 && balance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Right
        if (balance < -1 && balance(node.right) <= 0)
            return rotateLeft(node);

        // Right Left
        if (balance < -1 && balance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Node min(Node node) {

        while (node.left != null)
            node = node.left;

        return node;
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(Node node) {

        if (node != null) {

            inorder(node.left);

            System.out.println(node.data);

            inorder(node.right);
        }
    }
}
