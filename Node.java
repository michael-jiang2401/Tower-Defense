import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Node here.
 * 
 * Takes in the current map from the world and converts it into a graph of nodes, each with a cost of 1. The 0's from the 2d array
 * represent the nodes. Anything else is ignored.
 * 
 * Calculates the shortest path from a target to the source. Used to give the enemy a path to the player,
 * while going around walls.
 * 
 * The algorithm calculates the cost to each node in the graph starting from the target. The cost of the
 * next node is equal to the cost of the previous node added to its own cost and so on until everything
 * is calculated. Then, from the target, the path to the source is done by taking the path with the lowest
 * cost. The source is then given the path and moves towards the target.
 * 
 * 
 * Site used to write Dijkstras shortest path algorithm (pseudo code and tutorial used):
 * https://brilliant.org/wiki/dijkstras-short-path-finder/
 * http://www.gitta.info/Accessibiliti/en/html/Dijkstra_learningObject1.html
 * https://www.youtube.com/watch?v=q3yKyE19OR0 (tutorial)
 * 
 * This version was modified from the original pseudo code to allow it to
 * work with my maps. However, most of what was on the websites and video is still here.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Node
{
    // pathing current cost
    private int myCost;

    private Node lowestCostParent;

    private String data;

    private int x;

    private int y;

    private Node[] children;

    private int[] costs;

    // very big number
    private static int infinity = 999999;

    //returns the x,y location in the world
    /**
     * Returns the X value of the location of a node on the screen
     * 
     * @return int The x coordinate of the node
     */
    public int realWorldX()
    {
        return x * 40;
    }

    /**
     * Returns the Y value of the location of a node on the screen
     * 
     * @return int The y coordinate of the node
     */
    public int realWorldY()
    {
        return y * 40;
    }

    //returns the center of a node given the x,y coordinates
    /**
     * Returns the X coordinate of the center of a node by adding half of its size
     * 
     * @return int The x coordinate of the node's center
     */
    public int realWorldXCenter()
    {
        return realWorldX() + 20;
    }

    /**
     * Returns the Y coordinate of the center of a node by adding half of its size
     * 
     * @return int The y coordinate of the node's center
     */
    public int realWorldYCenter()
    {
        return realWorldY() + 20;
    }
    //returns the x,y coordinates

    /**
     * Returns the x value of the node before scaling to the world size
     * 
     * @return int The x value of the node
     */
    public int returnX()
    {
        return x;
    }

    /**
     * Returns the y value of the node before scaling to the world size
     * 
     * @return int The y value of the node
     */
    public int returnY()
    {
        return y;
    }
    
    /**
     * Calculates the distance from one node to the other. Not the true distance, 
     * just a calculation to get an idea of the distance.
     * 
     * @return int The distance between the two nodes
     */
    public int distanceToWorldObject(int otherX, int otherY)
    {
        int result = Math.max(Math.abs(realWorldXCenter() - otherX), Math.abs(realWorldYCenter() - otherY));
        return result;
    }

    //Node constructors
    /**
     * Constructs a Node
     */
    public Node()
    {}

    /**
     * Constructs a node that saves the value of (x * WorldHeight + y)
     * 
     * @param data The value of (x * WorldHeight + y)
     */
    public Node(String data)
    {
        this.data = data;
    }

    /**
     * Constructs a node that saves the value of (x * WorldHeight + y) and 
     * the x and y position of the node.
     * 
     * @param data The value of (x * WorldHeight + y)
     * @param x The x position of the Node
     * @param y The y position of the Node
     */
    public Node(String data, int x, int y)
    {
        this.data = data;
        this.x = x;
        this.y = y;
    }
    //sets children of the node
    /**
     * Sets the children of the node
     * 
     * @param children The children of the node
     */
    public void SetChildren(Node[] children)
    {
        this.children = children;
    }
    //makes costs for the children
    /**
     * Makes costs for the children of the node
     * 
     * @param children The children of the node
     * @param cost The cost of the node
     */
    public void SetChildren(Node[] children, int cost)
    {
        this.children = children;

        costs = new int[children.length];

        for(int i=0; i<costs.length; i++)
        {
            costs[i] = cost;
        }
    }
    //sets the costs
    /**
     * Sets the costs of the nodes
     * 
     * @param costs An array of ints to store the costs of the nodes
     */
    public void SetCosts(int[] costs)
    {
        this.costs = costs;
    }
    //returns the children
    /**
     * Returns the children
     * 
     * @return The children of a node
     */
    public Node[] GetChildren()
    {
        return children;
    }
    //returns the costs
    /**
     * Returns an array of integers that represent the costs of the nodes
     * 
     * @return int[] An array of node costs
     */
    public int[] GetCosts()
    {
        return costs;
    }

    //sets children and costs
    /**
     * Sets the children of a node and their costs
     * 
     * @param children The children of a node
     * @param costs The cost of the children saved in an array of integers
     */
    public void SetChildrenAndCosts(Node[] children, int[] costs)
    {
        SetChildren(children);
        SetCosts(costs);
    }
    //sets the cost
    /**
     * Sets the cost of a node by saving it to the global variable
     * 
     * @param myCost The cost of the node being set
     */
    public void SetMyCost(int myCost)
    {
        this.myCost = myCost;
    }

    /**
     * Sets the cost of a node and the parent of the node
     * 
     * @param myCost The cost of the node
     * @param parent The parent of the node
     */
    public void SetMyCost(int myCost, Node parent)
    {
        this.myCost = myCost;
        lowestCostParent = parent;
    }
    //returns the cost
    /**
     * Returns the cost of the node
     * 
     * @return int The integer value of the cost of a node
     */
    public int GetMyCost()
    {
        return myCost;
    }
    //returns the next node with the lowest cost
    /**
     * Returns the node that has the lowest cost
     * 
     * @return Node The node that has the lowest cost from the current node
     */
    public Node GetLowestCostParent()
    {
        return lowestCostParent;
    }

    // find the path from start to target
    // the world is described by "graph"
    /**
     * Finds the shortest path in a given graph of nodes from the start to end
     * 
     * @param graph The graph of nodes that will  be used to find the shortest path
     * @param start The starting Node
     * @param target The target Node
     * 
     * @return Node[] The array of nodes that lead from the start to target
     */
    public static Node[] findPath(Node[] graph, Node start, Node target)
    {
        Node[] graphWithCosts = dijkstra(graph, start);

        Node[] result = new Node[graph.length];

        Node current = target;

        int i = 0;
        while(current != null)
        {
            result[i++] = current;

            current = current.GetLowestCostParent();

        }

        return result;
    }

    // maybe make a copy of graph instead of working in-place
    /**
     * Calculates the shortest path in a graph of nodes, begins at the start Node
     * 
     * @param graph The graph of nodes being used to calculate the path
     * @param start The starting Node
     * @return Node[] The path of nodes from start to target
     */
    private static Node[] dijkstra(Node[] graph, Node start)
    {
        Node[] q = new Node[graph.length];

        for(int i =0; i< graph.length; i++)
        {
            if(graph[i] != start)
            {
                graph[i].SetMyCost(infinity);//sets the cost of the nodes that aren't yet visited to a large number      
            }
            else
            {
                // starting cost is 0
                graph[i].SetMyCost(0);
            }

            q[i] = graph[i];

        }

        while(q.length > 0)//main loop
        {
            Node v = getMinMyCost(q);//in the first run, this vertex is the source node
            q = removeElement(q, v);

            Node[] children = v.GetChildren();
            int[] costs = v.GetCosts();

            if (children == null)
            {
                children = new Node[0];
            }

            for(int i=0; i < children.length; i++)//where neighbor u has not yet been removed from Q
            {
                int alt = v.GetMyCost() + costs[i];

                if(alt < children[i].GetMyCost())//a shorter path to u has been found
                {
                    children[i].SetMyCost(alt, v);//update new distance of u
                }
            }
        }

        return graph;
    }
    //gets the minimum cost of the available nodes
    /**
     * Returns the node with the smallest cost of all the nodes in the array
     * 
     * @param input The array of possible next nodes
     * @return Node The node with the smallest cost
     */
    private static Node getMinMyCost(Node[] input)
    {
        int myCost = input[0].GetMyCost();
        Node result = input[0];

        for(int i=1;i < input.length; i++)
        {
            if (myCost > input[i].GetMyCost())
            {
                myCost = input[i].GetMyCost();
                result = input[i];
            }
        }
        return result;
    }
    //removes a node from the array by making a new one without it
    /**
     * Removes a specified Node from the array of nodes
     * 
     * @param input The array of nodes
     * @param deleteMe The node being removed from the array of nodes
     * @return Node[] The modified array of nodes
     */
    private static Node[] removeElement(Node[] input, Node deleteMe) 
    {
        Node result[] = new Node[input.length - 1];

        int j = 0;

        for(int i = 0; i < input.length; i++)
        {
            if (input[i] != deleteMe)
            {
                result[j++] = input[i];
            }
        }

        return result;
    }
    //creates a graph based on the given map from the world
    /**
     * Creates a graph based on the given map from the world
     * 
     * @return Node[] The graph of nodes created
     */
    public static Node[] GetGraphFromMapArray()
    {
        return GetGraphFromMapArray(MyWorld.getMap(), MyWorld.WorldSizeX(), MyWorld.WorldSizeY());
    }
    //takes a map from the world and its x,y size and converts it
    //into a graph of nodes
    /**
     * Takes a map from the world and its x,y size and converts it into a graph of nodes.
     * Nodes that are null are considered valid
     * 
     * @param mapArray The array of Towers currently in the map
     * @param SIZEX The x size of the map
     * @param SIZEY The y size of the map
     * @return Node[] The graph of nodes created
     */
    private static Node[] GetGraphFromMapArray(Tower[][] mapArray, int SIZEX, int SIZEY)
    {
        Node[] graph = new Node[SIZEX * SIZEY];

        for(int x=0; x<SIZEX; x++)
        {
            for(int y=0; y<SIZEY; y++)
            {
                int i = x * SIZEY + y;

                graph[i] = new Node(String.valueOf(i), x, y);
            }
        }
        for(int x=0; x<SIZEX; x++)
        {
            for(int y=0; y<SIZEY; y++)//only gets the values that are marked as 0
            {
                int i = x * SIZEY + y;

                Node[] children = new Node[4];

                // left goes to 0
                if (x > 0 && mapArray[x-1][y] == null)
                {
                    int j = (x-1)*SIZEY + y;
                    children[0] = graph[j];
                }

                // right goes to 1
                if (x < SIZEX - 1 && mapArray[x+1][y] == null)
                {
                    int j = (x+1)*SIZEY + y;
                    children[1] = graph[j];
                }

                // up goes to 2
                if (y > 0 && mapArray[x][y-1] == null)
                {
                    int j = x*SIZEY + (y-1);
                    children[2] = graph[j];
                }

                // down goes to 3
                if (y < SIZEY - 1 && mapArray[x][y+1] == null)
                {
                    int j = x*SIZEY + (y+1);
                    children[3] = graph[j];
                }

                graph[i].SetChildren(getNotNullNodes(children), 1);
            }
        }

        return graph;
    }

    //returns the array of nodes that aren't null
    /**
     * Gets the nodes that aren't null in the input of nodes and returns it
     * 
     * @param input The array of Nodes being filtered
     * @return The new array of nodes only including the null values
     */
    public static Node[] getNotNullNodes(Node[] input)
    {
        int nullCount = 0;//counts null nodes

        for(int i = 0; i < input.length; i++)//goes through array and finds null nodes
        {
            if (input[i] == null)
            {
                nullCount++;
            }
        }

        if (nullCount == 0)//if there aren't any null nodes
        {
            return input;
        }

        Node[] result = new Node[input.length - nullCount];//creates a new array with the not null nodes
        int j = 0;

        for(int i = 0; i < input.length; i++)
        {
            if (input[i] != null)
            {
                result[j++] = input[i];
            }
        }

        return result;
    }

    // get path from enemy to target
    /**
     * Gets the path from the enemy to the target
     * 
     * @param enemy The current enemy trying to find a path
     * @param target The target that the enemy is trying to reach
     * @return Node[] The graph of nodes that the enemy needs to follow to get to the target
     */
    public static Node[] getPath(Enemy enemy, Target target)
    {
        return getPath(enemy, new Coordinates(target.getX(), target.getY()));
    }

    //calculates the path from the enemy to the given target.
    /**
     * Gets the path from enemy to the coordinates of the target
     * @param enemy The enemy trying to find a path
     * @param target The coordinates of the target that the enemy is trying to reach
     * @return Node[] The graph of nodes that the enemy needs to follow to get to the target
     */
    public static Node[] getPath(Actor enemy, Coordinates target)
    {
        Node[] graph = Node.GetGraphFromMapArray();//gets the current map and turns it into a graph

        Node startNode = graph[enemy.getX() / 40 * MyWorld.WorldSizeY() + enemy.getY()/40];//gets the starting node
        Node targetNode = graph[target.getX() / 40 * MyWorld.WorldSizeY() + target.getY() / 40];//gets the target node

        Node[] path = Node.findPath(graph, startNode, targetNode);//gets the path

        path = Node.getNotNullNodes(path);//removes the null nodes
        return path;
    }

}
