package graph306;//####[1]####
//####[1]####
import pt.queues.FifoLifoQueue;//####[2]####
import pt.runtime.*;//####[3]####
import java.util.ArrayList;//####[5]####
import java.util.Arrays;//####[6]####
import java.util.LinkedList;//####[7]####
import java.util.List;//####[8]####
import data_structures.AdjacencyList;//####[9]####
import data_structures.NodeObject;//####[10]####
import data_structures.UserOptions;//####[11]####
import visual.GraphVisualiser;//####[12]####
//####[12]####
//-- ParaTask related imports//####[12]####
import pt.runtime.*;//####[12]####
import java.util.concurrent.ExecutionException;//####[12]####
import java.util.concurrent.locks.*;//####[12]####
import java.lang.reflect.*;//####[12]####
import pt.runtime.GuiThread;//####[12]####
import java.util.concurrent.BlockingQueue;//####[12]####
import java.util.ArrayList;//####[12]####
import java.util.List;//####[12]####
//####[12]####
public class ParallelSearchTree extends SolutionTree {//####[18]####
    static{ParaTask.init();}//####[18]####
    /*  ParaTask helper method to access private/protected slots *///####[18]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[18]####
        if (m.getParameterTypes().length == 0)//####[18]####
            m.invoke(instance);//####[18]####
        else if ((m.getParameterTypes().length == 1))//####[18]####
            m.invoke(instance, arg);//####[18]####
        else //####[18]####
            m.invoke(instance, arg, interResult);//####[18]####
    }//####[18]####
//####[20]####
    int semaphore = UserOptions.getInstance().getParallelThreads();//####[20]####
//####[23]####
    TaskIDGroup taskGroup = new TaskIDGroup(UserOptions.getInstance().getParallelThreads() + 5);//####[23]####
//####[24]####
    GraphVisualiser visualFrame = null;//####[24]####
//####[30]####
    /**Constructor to initialize the arguments
	 * @param inputGraph - Storing the input Adjacency list
	 *///####[30]####
    public ParallelSearchTree(AdjacencyList inputGraph) {//####[30]####
        super(inputGraph);//####[31]####
        if (UserOptions.getInstance().isVisible()) //####[33]####
        {//####[33]####
            visualFrame = new GraphVisualiser(inputGraph, this);//####[34]####
        }//####[35]####
    }//####[36]####
//####[42]####
    /** recursiveMethod is the parallel version of calculateTime from the SolutionTree.
	*@param currentNode - the currentNode 
	*@param nodesToCheck -  List of nodes that have not yet been seen in this branch of the tree
	*@param isSubTask - Checks which node is the root of the thread or not
	**///####[42]####
    public void recursiveMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isSubtask) {//####[42]####
        if (nodesToCheck.size() == 0) //####[45]####
        {//####[45]####
            if (UserOptions.getInstance().isVisible() && visualFrame != null) //####[47]####
            {//####[47]####
                visualFrame.notifyFirstGraph(currentNode);//####[48]####
            }//####[49]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[51]####
            {//####[51]####
                minimumTime = maxTimeAtPoint(currentNode);//####[54]####
                bestSchedule = currentNode.getCurrentPath();//####[55]####
                if (UserOptions.getInstance().isVisible() && visualFrame != null) //####[57]####
                {//####[57]####
                    visualFrame.notifyGraph(currentNode, minimumTime, endArray(bestSchedule));//####[58]####
                }//####[59]####
            }//####[60]####
            return;//####[61]####
        }//####[62]####
        if (minimumTime != Integer.MAX_VALUE) //####[65]####
        {//####[65]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[67]####
            {//####[67]####
                return;//####[68]####
            }//####[69]####
            if (calculateLowerBound(currentNode, nodesToCheck) >= minimumTime) //####[71]####
            {//####[71]####
                return;//####[72]####
            }//####[73]####
        }//####[74]####
        TaskID newTask = null;//####[78]####
        for (String nodeToCheckStr : nodesToCheck) //####[79]####
        {//####[79]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[80]####
            {//####[80]####
                int count = 0;//####[81]####
                for (int i = 0; i < numberofProcessors; i++) //####[82]####
                {//####[82]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[83]####
                    {//####[83]####
                        count++;//####[84]####
                    }//####[85]####
                }//####[86]####
                int killtree = 0;//####[88]####
                if (count >= 2) //####[89]####
                {//####[89]####
                    killtree = count - 1;//####[90]####
                }//####[91]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[92]####
                {//####[92]####
                    NodeObject newNode = createNextNode(currentNode, nodeToCheckStr, j);//####[93]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[96]####
                    newUpdatedListWithoutCurrentNode.remove(nodeToCheckStr);//####[97]####
                    nodeNumber++;//####[98]####
                    if (UserOptions.getInstance().isVisible() && visualFrame != null) //####[99]####
                    {//####[99]####
                        visualFrame.notifyParallelGraph(nodeNumber, semaphore);//####[100]####
                    }//####[101]####
                    if (isSubtask) //####[103]####
                    {//####[103]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, true);//####[104]####
                        continue;//####[105]####
                    }//####[106]####
                    if (semaphore == 0) //####[108]####
                    {//####[108]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, false);//####[109]####
                    } else {//####[110]####
                        semaphore--;//####[111]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[112]####
                        taskGroup.add(newTask);//####[114]####
                    }//####[115]####
                }//####[116]####
            }//####[117]####
        }//####[118]####
        if (isSubtask) //####[120]####
        {//####[120]####
            return;//####[121]####
        }//####[122]####
        try {//####[124]####
            taskGroup.waitTillFinished();//####[125]####
        } catch (Exception e) {//####[126]####
            e.printStackTrace();//####[127]####
        }//####[128]####
    }//####[129]####
//####[132]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[132]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            try {//####[132]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[132]####
                    NodeObject.class, List.class//####[132]####
                });//####[132]####
            } catch (Exception e) {//####[132]####
                e.printStackTrace();//####[132]####
            }//####[132]####
        }//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setTaskIdArgIndexes(0);//####[132]####
        taskinfo.addDependsOn(currentNode);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(0);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setTaskIdArgIndexes(1);//####[132]####
        taskinfo.addDependsOn(nodesToCheck);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[132]####
        taskinfo.addDependsOn(currentNode);//####[132]####
        taskinfo.addDependsOn(nodesToCheck);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(0);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setTaskIdArgIndexes(1);//####[132]####
        taskinfo.addDependsOn(nodesToCheck);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(1);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(1);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setTaskIdArgIndexes(0);//####[132]####
        taskinfo.addDependsOn(currentNode);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(0, 1);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[132]####
        recursiveMethod(currentNode, nodesToCheck, true);//####[133]####
        semaphore++;//####[134]####
    }//####[135]####
//####[135]####
//####[142]####
    /**createNextNode method is used to create the next valid node in the tree
	*@param currentNode - currentNode on the tree
	*@param nodeToCheckStr - the name of the node
	*@param processorNumber - the processor the node is in
	**///####[142]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[142]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[145]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[146]####
        String newNodeName = nodeToCheckStr;//####[149]####
        int newProcessor = processorNumber;//####[150]####
        int nodalWeight = getNodalWeight(newNodeName);//####[151]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[152]####
        int newEndTime = newStartTime + nodalWeight;//####[153]####
        processorArray[newProcessor] = newEndTime;//####[154]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[157]####
        return nextNode;//####[158]####
    }//####[159]####
}//####[159]####
