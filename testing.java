public class testing {
    public static void main(String[] args) {
        BinarySearchTree<String, String> tree = new BinarySearchTree<>();
        tree.put( "Larry", "larry@mtu.edu" );
        String value = tree.remove( "Larry" );
        System.out.println(value);
    }
}
