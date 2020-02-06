package org.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class TreeTest {

    @Test
    void SerializeSimple() {
        TreeManagement treeManagement = new TreeManagement();
        TreeNode source = getEurope( 1);
        String serializeSt = treeManagement.serialize( source );
        
        System.out.println(" Europe_Country Tree : "+serializeSt);
        TreeNode copy;
        try {
            copy = treeManagement.unserialize( serializeSt );
            boolean isEqual = treeManagement.compare( source, copy );
            assert(isEqual);
            System.out.println(" Europe_Country: unserialize is OK");

        } catch (Exception e) {
            System.out.println("Exception "+e.toString());
            fail("Can't deserialize");
        }
        

    }
    @Test
    void SerializeDepth2() {
        TreeManagement treeManagement = new TreeManagement();
        TreeNode source = getEurope( 2 );
        String serializeSt = treeManagement.serialize( source );
        
        System.out.println(" Europe_Cities Tree : "+serializeSt);
        TreeNode copy;
        try {
            copy = treeManagement.unserialize( serializeSt );
            boolean isEqual = treeManagement.compare( source, copy );
            assert(isEqual);
            System.out.println(" Europe_Cities: unserialize is OK");

        } catch (Exception e) {
            System.out.println("Exception "+e.toString());
            fail("Can't deserialize");
        }
    }
    @Test
    void SerializeDepth3() {
        TreeManagement treeManagement = new TreeManagement();
        TreeNode source = getEurope( 3);
        String serializeSt = treeManagement.serialize( source );
        
        System.out.println(" Europe_Cities Tree : "+serializeSt);
        TreeNode copy;
        try {
            copy = treeManagement.unserialize( serializeSt );
            boolean isEqual = treeManagement.compare( source, copy );
            assert(isEqual);
            System.out.println(" Europe_Cities: unserialize is OK");

        } catch (Exception e) {
            System.out.println("Exception "+e.toString());
            fail("Can't deserialize");
        }
    }

    
    public TreeNode getEurope( int depth) {
        TreeNode europe = new TreeNode("Europe");
        TreeNode france = europe.addChild( "France");
        if (depth> 1)
        {
            TreeNode paris = france.addChild("Paris");
            if (depth>2) {
                paris.addChild("IV").addChild("Rue Mouftard");
                paris.addChild("III").addChild("Rue de Tivoli");
                }
            
            france.addChild("Lyon");
            france.addChild("Toulouse");
            
            TreeNode grenoble= france.addChild("Grenoble");
            if (depth>2) {
                grenoble.addChild("St martin d'heres").addChild("Jean Jaures");
                paris.addChild("Meylan").addChild("Route de vence");
                }

        }
        TreeNode germany =europe.addChild( "Germany");
        if (depth>1)
        {
            germany.addChild("Berlin");
            germany.addChild("Munich");
            germany.addChild("Hambourg");
            germany.addChild("Koln");
        }
        return europe;
        
    }
}
