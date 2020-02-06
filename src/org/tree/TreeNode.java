package org.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    public String id;
    public List<TreeNode> children = new ArrayList<TreeNode>();
    
    public TreeNode( String id ) {
        this.id  = id;
    }

    public TreeNode addChild( String childId ) {
        TreeNode child = new TreeNode(childId);
        children.add( child );
        return child;
    }
    public TreeNode addChild( TreeNode child ) {
        children.add( child );
        return child;
    }
    
    /**
     * for debug
     */
    public String toString() {
        return id;
    }

}
