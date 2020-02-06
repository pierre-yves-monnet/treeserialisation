package org.tree;

public class TreeManagement {
    
    /**
     * produce a result like 
     * EUROPE:(FRANCE (PARIS, LYON), GERMANY)
     * return : 
     *   - the node ID
     *   - if the node as kids, then open a (), collect inside children separate by a comma 
     * @param tree
     * @return
     */
    public String serialize(TreeNode sourceNode) {
        StringBuffer result= new StringBuffer();
        result.append(sourceNode.id);
        if (sourceNode.children.size()>0)
        {
            result.append("(");
            for (int i=0;i<sourceNode.children.size();i++)
            {
                if (i>0)
                    result.append(",");
                result.append( serialize( sourceNode.children.get(i )));
            }
            result.append(")");
        }
        return result.toString();
    }

    /**
     * Structure is <ID>
     * or ID( <kids>,<kids>,<kids>)
     * @param deserializeSt
     * @return
     * @throws Exception 
     */
    public TreeNode unserialize(String deserializeSt) throws Exception {
        TreeNode resultNode = null;

        int posParenthesis=deserializeSt.indexOf("(");
        if (posParenthesis==-1)
        {
            // no brother, no kids : this is a leaf
            resultNode = new TreeNode(deserializeSt);
            return resultNode;
        }
        // so I have kids
        String nodeId = deserializeSt.substring(0, posParenthesis);
        resultNode = new TreeNode(nodeId);
        // attention, my kids may have subchild : that's here the main part
        // (France(Paris,Lyon,Toulouse,Grenoble),Germany(Berlin,Munich,Hambourg,Koln), Suisse
        // my kids are France, Germany
        int indexAdvance = posParenthesis+1;
        while (indexAdvance<deserializeSt.length())
        {
            // end parenthesis ? no more kids.
            if (deserializeSt.charAt(indexAdvance) ==')') 
                return resultNode;

            //  my kids goes until an open parenthesis or a comma
            int posChildComma = deserializeSt.indexOf(",", indexAdvance);
            int posChildParenthesis = deserializeSt.indexOf("(", indexAdvance);
            if (posChildComma==-1 && posChildParenthesis== -1) {
                // my next (and last) kids does not have any child
                resultNode.addChild( deserializeSt.substring(indexAdvance, deserializeSt.length()-1));
                return resultNode;
            }
            // we detect here a comma or a parenthesis
            if (posChildParenthesis==-1)
                posChildParenthesis = deserializeSt.length()+1;
            if (posChildComma==-1)
                posChildComma = deserializeSt.length()+1;
            
            if (posChildComma < posChildParenthesis) {
                // we detect a brother first
                resultNode.addChild( deserializeSt.substring(indexAdvance, posChildComma));
                indexAdvance= posChildComma+1;
            }
            else {
                // the parenthesis
                // so here we have to search the END PARENTHESIS
                // France(Paris,Lyon,Toulouse,Grenoble) 
                //                                    ^
                // France(Paris(9Eme district),Lyon,Toulouse(blagnac,ramonville),Grenoble)
                //                                                                       ^
                int countOpenParenthesis=1;
                int positionEndChild=-1;
                for (int j=posChildParenthesis+1;j<deserializeSt.length(); j++) {
                    if (deserializeSt.charAt( j ) == '(')
                        countOpenParenthesis++;
                    if (deserializeSt.charAt( j ) == ')')
                        countOpenParenthesis--;
                    if (countOpenParenthesis==0)
                    {
                        positionEndChild = j;
                        break; // here we are !
                    }
                }
                if (countOpenParenthesis!=0)
                    throw new Exception("Bad format, d'ont detect end of parenthesis in ("+deserializeSt.substring(indexAdvance));
                // we got the node
                String subNodeSt = deserializeSt.substring(indexAdvance, positionEndChild+1);
                resultNode.addChild(  unserialize( subNodeSt ));
                indexAdvance = positionEndChild+1;
                // special case, if the item at index is a , then advance by one to skip it. It may be a )
                if (indexAdvance < deserializeSt.length() && (deserializeSt.charAt( indexAdvance) == ','))
                    indexAdvance++;
                
            }
        }
            
        return resultNode;
    }

    
    /**
     * compare two nodes
     * 
     * @param sourceNode
     * @param compareNode
     * @return
     */
    public boolean compare(TreeNode sourceNode, TreeNode compareNode) {
        if (! sourceNode.id.equals( compareNode.id )) {
            return false;
        }
        if (sourceNode.children.size() != compareNode.children.size())
            return false;
        for (int i=0; i<sourceNode.children.size();i++)
        {
            if (! compare(sourceNode.children.get(i ), compareNode.children.get( i )))
                return false;
        }
        return true;
    }
}
