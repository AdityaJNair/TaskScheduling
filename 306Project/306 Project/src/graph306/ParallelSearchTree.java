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
public class ParallelSearchTree extends SolutionTree {//####[14]####
    static{ParaTask.init();}//####[14]####
    /*  ParaTask helper method to access private/protected slots *///####[14]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[14]####
        if (m.getParameterTypes().length == 0)//####[14]####
            m.invoke(instance);//####[14]####
        else if ((m.getParameterTypes().length == 1))//####[14]####
            m.invoke(instance, arg);//####[14]####
        else //####[14]####
            m.invoke(instance, arg, interResult);//####[14]####
    }//####[14]####
//####[16]####
    int semaphore = UserOptions.getInstance().getParallelThreads();//####[16]####
//####[17]####
    FifoLifoQueue<List<NodeObject>> bestScheduleThreaded = new FifoLifoQueue<List<NodeObject>>();//####[17]####
//####[18]####
    FifoLifoQueue<Integer> bestTimeThreaded = new FifoLifoQueue<Integer>();//####[18]####
//####[19]####
    List<String> sourceNodes = inputGraph.getSourceNodes();//####[19]####
//####[20]####
    TaskIDGroup taskGroup = new TaskIDGroup(UserOptions.getInstance().getParallelThreads() + 5);//####[20]####
//####[21]####
    GraphVisualiser visualFrame = null;//####[21]####
//####[26]####
    /**
	 * @param inputGraph
	 *///####[26]####
    public ParallelSearchTree(AdjacencyList inputGraph) {//####[26]####
        super(inputGraph);//####[27]####
        bestTimeThreaded.addGlobal(Integer.MAX_VALUE);//####[28]####
        if (UserOptions.getInstance().isVisible()) //####[30]####
        {//####[30]####
            visualFrame = new GraphVisualiser(inputGraph, this);//####[31]####
        }//####[32]####
    }//####[33]####
//####[35]####
    public void recursiveMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isSubtask) {//####[35]####
        if (nodesToCheck.size() == 0) //####[38]####
        {//####[38]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[41]####
            {//####[41]####
                minimumTime = maxTimeAtPoint(currentNode);//####[44]####
                bestSchedule = currentNode.getCurrentPath();//####[45]####
                if (UserOptions.getInstance().isVisible() == true) //####[47]####
                {//####[47]####
                    visualFrame.notifyGraph(currentNode);//####[48]####
                }//####[49]####
            }//####[50]####
            return;//####[51]####
        }//####[52]####
        if (minimumTime != Integer.MAX_VALUE) //####[55]####
        {//####[55]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[57]####
            {//####[57]####
                return;//####[58]####
            }//####[59]####
            if (calculateLowerBound(currentNode, nodesToCheck) >= minimumTime) //####[61]####
            {//####[61]####
                return;//####[62]####
            }//####[63]####
        }//####[64]####
        TaskID newTask = null;//####[66]####
        for (String nodeToCheckStr : nodesToCheck) //####[67]####
        {//####[67]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[68]####
            {//####[68]####
                int count = 0;//####[69]####
                for (int i = 0; i < numberofProcessors; i++) //####[70]####
                {//####[70]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[71]####
                    {//####[71]####
                        count++;//####[72]####
                    }//####[73]####
                }//####[74]####
                int killtree = 0;//####[75]####
                if (count >= 2) //####[76]####
                {//####[76]####
                    killtree = count - 1;//####[77]####
                }//####[78]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[79]####
                {//####[79]####
                    NodeObject newNode = createNextNode(currentNode, nodeToCheckStr, j);//####[80]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[83]####
                    newUpdatedListWithoutCurrentNode.remove(nodeToCheckStr);//####[84]####
                    nodeNumber++;//####[85]####
                    if (isSubtask) //####[86]####
                    {//####[86]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, true);//####[87]####
                        continue;//####[88]####
                    }//####[89]####
                    if (semaphore == 0) //####[91]####
                    {//####[91]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, false);//####[92]####
                    } else {//####[93]####
                        semaphore--;//####[94]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[95]####
                        taskGroup.add(newTask);//####[97]####
                    }//####[98]####
                }//####[99]####
            }//####[100]####
        }//####[101]####
        if (isSubtask) //####[102]####
        {//####[102]####
            return;//####[103]####
        }//####[104]####
        try {//####[105]####
            taskGroup.waitTillFinished();//####[106]####
        } catch (Exception e) {//####[107]####
            e.printStackTrace();//####[108]####
        }//####[109]####
    }//####[110]####
//####[112]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[112]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            try {//####[112]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[112]####
                    NodeObject.class, List.class//####[112]####
                });//####[112]####
            } catch (Exception e) {//####[112]####
                e.printStackTrace();//####[112]####
            }//####[112]####
        }//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(0);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(1);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(1);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(1);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(1);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(0);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0, 1);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[112]####
        recursiveMethod(currentNode, nodesToCheck, true);//####[113]####
        semaphore++;//####[114]####
    }//####[115]####
//####[115]####
//####[117]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[117]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[120]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[121]####
        String newNodeName = nodeToCheckStr;//####[124]####
        int newProcessor = processorNumber;//####[125]####
        int nodalWeight = getNodalWeight(newNodeName);//####[126]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[127]####
        int newEndTime = newStartTime + nodalWeight;//####[128]####
        processorArray[newProcessor] = newEndTime;//####[129]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[132]####
        return nextNode;//####[133]####
    }//####[134]####
}//####[134]####
