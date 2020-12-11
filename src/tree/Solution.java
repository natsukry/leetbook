package tree;

import java.util.Arrays;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 9, 20, -1, -1, 15, 7};  // -1 indicates a null node
        int[] newArr = Arrays.copyOfRange(arr,1,2);
        System.out.println(newArr);
    }

    /**654. 最大二叉树
     给定一个不含重复元素的整数数组。一个以此数组构建的最大二叉树定义如下：

     二叉树的根是数组中的最大元素。
     左子树是通过数组中最大值左边部分构造出的最大二叉树。
     右子树是通过数组中最大值右边部分构造出的最大二叉树。
     通过给定的数组构建最大二叉树，并且输出这个树的根节点。

     示例 ：

     输入：[3,2,1,6,0,5]
     输出：返回下面这棵树的根节点：

     6
     /   \
     3     5
     \    /
     2  0
     \
     1*/
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums.length <= 0) return null;
        int maxPos = findMaxPos(nums,0,nums.length-1);
        TreeNode root = new TreeNode(nums[maxPos]);
        root.left = constructMaximumBinaryTree(Arrays.copyOfRange(nums,0,maxPos));
        root.right = constructMaximumBinaryTree(Arrays.copyOfRange(nums,maxPos,nums.length-1));
        return root;
    }
    private int findMaxPos(int[] arr, int left, int right){
        if (right-left == 1) return left;
        int max = arr[left];
        int pos = left;
        for (int i = left+1; i <= right; i++) {
            if (arr[i]>max){
                max = arr[i];
                pos = i;
            }
        }
        return pos;
    }

    /**
     * 114. 二叉树展开为链表<br>
     * 1、将root的左子树和右子树拉平。
     * <p>
     * 2、将root的右子树接到左子树下方，然后将整个左子树作为右子树。
     */
    public void flatten(TreeNode root) {
        if (root == null) return;
        flatten(root.left);
        flatten(root.right);
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = null;
        root.right = left;

        // 找到当前右子树的末端
        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        p.right = right;

    }

    /**
     * 116. 填充每个节点的下一个右侧节点指针<br/>
     * 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     * <p>
     * struct Node {
     * int val;
     * Node *left;
     * Node *right;
     * Node *next;
     * }
     * </p>
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     */
    public Node connect(Node root) {
        if (root == null) return null;
        connectTwoNode(root.left, root.right);
        return root;
    }

    private void connectTwoNode(Node node1, Node node2) {
        if (node1 == null || node2 == null) return;
        node1.next = node2;
        connectTwoNode(node1.left, node1.right);
        connectTwoNode(node2.left, node2.right);
        connectTwoNode(node1.right, node2.left);
    }

    /**
     * 翻转一棵二叉树
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode p = root.left;
        root.left = root.right;
        root.right = p;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    /**
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     * 假设一个二叉搜索树具有如下特征：
     * 节点的左子树只包含小于当前节点的数。节点的右子树只包含大于当前节点的数。所有左子树和右子树自身必须也是二叉搜索树。
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        if (root.left != null && root.right != null) {
            return root.left.val < root.val && root.right.val > root.val && isValidBST(root.right) && isValidBST(root.left);
        } else if (root.left != null) {
            return root.left.val < root.val && isValidBST(root.left);
        } else if (root.right != null) {
            return root.right.val < root.val && isValidBST(root.right);
        }
        // no child
        else return true;
    }

    /**
     * 给定二叉搜索树（BST）的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 NULL。
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        if (val < root.val) return searchBST(root.left, val);
        else return searchBST(root.right, val);
    }

    /**
     * 给定二叉搜索树（BST）的根节点和要插入树中的值，将值插入二叉搜索树。
     * 返回插入后二叉搜索树的根节点。 输入数据 保证 ，新值和原始二叉搜索树中的任意节点值都不同。
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        else if (val < root.val) root.left = insertIntoBST(root.left, val);
        else root.right = insertIntoBST(root.right, val);
        return root;
    }

    /**
     * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的key对应的节点，并保证二叉搜索树的性质不变。
     * 返回二叉搜索树（有可能被更新）的根节点的引用。
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (root.val == key) {
            // found
            // 1. no child
            if (root.left == null && root.right == null) return null;
                // 2. only one child
            else if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
                // 3. two children, find min to replace
            else {
                TreeNode minNode = getMinNode(root.right); // replace by minval in right sub tree
                root.val = minNode.val;
                root.right = deleteNode(root.right, minNode.val);
            }
        } else if (key < root.val) root.left = deleteNode(root.left, key);
        else if (key > root.val) root.right = deleteNode(root.right, key);
        return root;
    }

    public TreeNode getMinNode(TreeNode root) {
        TreeNode p = root;
        while (p.left != null)
            p = p.left;
        return p;
    }

    /**
     * 给定两个二叉树，编写一个函数来检验它们是否相同。
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            if (p == null && q == null) return true;
            else return false;
        } else if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    void traverse(TreeNode root) {
        if (root == null) return;
        // visit
        traverse(root.left);
        traverse(root.right);
    }

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树(一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1)
     * 输入：root = [3,9,20,null,null,15,7]
     * 输出：true
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        } else {
            return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
        }
    }

    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return Math.max(height(root.left), height(root.right)) + 1;
        }
    }

    // build tree by preorder and inorder
    TreeNode buildTree(int[] preorder, int preStart, int preEnd,
                       int[] inorder, int inStart, int inEnd, Map<Integer, Integer> inMap) {

        if (preStart > preEnd || inStart > inEnd) return null;

        TreeNode root = new TreeNode(preorder[preStart]);
        int inRoot = inMap.get(root.val);
        int numsLeft = inRoot - inStart;

        root.left = buildTree(preorder, preStart + 1, preStart + numsLeft,
                inorder, inStart, inRoot - 1, inMap);
        root.right = buildTree(preorder, preStart + numsLeft + 1, preEnd,
                inorder, inRoot + 1, inEnd, inMap);
        return root;
    }

    TreeNode BuildTree2(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd, Map<Integer, Integer> inMap) {
        TreeNode root = new TreeNode();
        return root;
    }
}


