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
//####[11]####
//-- ParaTask related imports//####[11]####
import pt.runtime.*;//####[11]####
import java.util.concurrent.ExecutionException;//####[11]####
import java.util.concurrent.locks.*;//####[11]####
import java.lang.reflect.*;//####[11]####
import pt.runtime.GuiThread;//####[11]####
import java.util.concurrent.BlockingQueue;//####[11]####
import java.util.ArrayList;//####[11]####
import java.util.List;//####[11]####
//####[11]####
public class ParallelSearchTree extends SolutionTree {//####[13]####
    static{ParaTask.init();}//####[13]####
    /*  ParaTask helper method to access private/protected slots *///####[13]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[13]####
        if (m.getParameterTypes().length == 0)//####[13]####
            m.invoke(instance);//####[13]####
        else if ((m.getParameterTypes().length == 1))//####[13]####
            m.invoke(instance, arg);//####[13]####
        else //####[13]####
            m.invoke(instance, arg, interResult);//####[13]####
    }//####[13]####
//####[15]####
    int semaphore = UserOptions.getInstance().getParallelThreads();//####[15]####
//####[16]####
    FifoLifoQueue<List<NodeObject>> bestScheduleThreaded = new FifoLifoQueue<List<NodeObject>>();//####[16]####
//####[17]####
    FifoLifoQueue<Integer> bestTimeThreaded = new FifoLifoQueue<Integer>();//####[17]####
//####[18]####
    List<String> sourceNodes = inputGraph.getSourceNodes();//####[18]####
//####[19]####
    TaskIDGroup taskGroup = new TaskIDGroup(UserOptions.getInstance().getParallelThreads());//####[19]####
//####[23]####
    /**
	 * @param inputGraph
	 *///####[23]####
    public ParallelSearchTree(AdjacencyList inputGraph) {//####[23]####
        super(inputGraph);//####[24]####
        bestTimeThreaded.addGlobal(Integer.MAX_VALUE);//####[25]####
    }//####[27]####
//####[29]####
    public void recursiveMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isSubtask) {//####[29]####
        if (nodesToCheck.size() == 0) //####[32]####
        {//####[32]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[35]####
            {//####[35]####
                minimumTime = maxTimeAtPoint(currentNode);//####[38]####
                bestSchedule = currentNode.getCurrentPath();//####[39]####
            }//####[40]####
            return;//####[41]####
        }//####[42]####
        if (minimumTime != Integer.MAX_VALUE) //####[45]####
        {//####[45]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[47]####
            {//####[47]####
                return;//####[48]####
            }//####[49]####
            if (calculateLowerBound(currentNode, nodesToCheck) >= minimumTime) //####[51]####
            {//####[51]####
                return;//####[52]####
            }//####[53]####
        }//####[54]####
        TaskID newTask = null;//####[56]####
        for (String nodeToCheckStr : nodesToCheck) //####[57]####
        {//####[57]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[58]####
            {//####[58]####
                int count = 0;//####[59]####
                for (int i = 0; i < numberofProcessors; i++) //####[60]####
                {//####[60]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[61]####
                    {//####[61]####
                        count++;//####[62]####
                    }//####[64]####
                }//####[65]####
                int killtree = 0;//####[66]####
                if (count >= 2) //####[67]####
                {//####[67]####
                    killtree = count - 1;//####[68]####
                }//####[69]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[70]####
                {//####[70]####
                    NodeObject newNode = createNextNode(currentNode, nodeToCheckStr, j);//####[71]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[74]####
                    newUpdatedListWithoutCurrentNode.remove(nodeToCheckStr);//####[75]####
                    nodeNumber++;//####[76]####
                    if (!isSubtask) //####[77]####
                    {//####[77]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, true);//####[78]####
                        continue;//####[79]####
                    }//####[80]####
                    if (semaphore == 0) //####[82]####
                    {//####[82]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, false);//####[83]####
                    } else {//####[84]####
                        semaphore--;//####[85]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[86]####
                        taskGroup.add(newTask);//####[88]####
                    }//####[89]####
                }//####[90]####
            }//####[91]####
        }//####[92]####
        if (!isSubtask) //####[93]####
        {//####[93]####
            return;//####[94]####
        }//####[95]####
        try {//####[96]####
            taskGroup.waitTillFinished();//####[97]####
        } catch (Exception e) {//####[98]####
            e.printStackTrace();//####[99]####
        }//####[100]####
    }//####[101]####
//####[103]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[103]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            try {//####[103]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[103]####
                    NodeObject.class, List.class//####[103]####
                });//####[103]####
            } catch (Exception e) {//####[103]####
                e.printStackTrace();//####[103]####
            }//####[103]####
        }//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[103]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setTaskIdArgIndexes(0);//####[103]####
        taskinfo.addDependsOn(currentNode);//####[103]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[103]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setQueueArgIndexes(0);//####[103]####
        taskinfo.setIsPipeline(true);//####[103]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[103]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setTaskIdArgIndexes(1);//####[103]####
        taskinfo.addDependsOn(nodesToCheck);//####[103]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[103]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[103]####
        taskinfo.addDependsOn(currentNode);//####[103]####
        taskinfo.addDependsOn(nodesToCheck);//####[103]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[103]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setQueueArgIndexes(0);//####[103]####
        taskinfo.setIsPipeline(true);//####[103]####
        taskinfo.setTaskIdArgIndexes(1);//####[103]####
        taskinfo.addDependsOn(nodesToCheck);//####[103]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[103]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setQueueArgIndexes(1);//####[103]####
        taskinfo.setIsPipeline(true);//####[103]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[103]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setQueueArgIndexes(1);//####[103]####
        taskinfo.setIsPipeline(true);//####[103]####
        taskinfo.setTaskIdArgIndexes(0);//####[103]####
        taskinfo.addDependsOn(currentNode);//####[103]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[103]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[103]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setQueueArgIndexes(0, 1);//####[103]####
        taskinfo.setIsPipeline(true);//####[103]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[103]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[103]####
    }//####[103]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[103]####
        recursiveMethod(currentNode, nodesToCheck, true);//####[104]####
        semaphore++;//####[105]####
    }//####[106]####
//####[106]####
//####[108]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[108]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[111]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[112]####
        String newNodeName = nodeToCheckStr;//####[115]####
        int newProcessor = processorNumber;//####[116]####
        int nodalWeight = getNodalWeight(newNodeName);//####[117]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[118]####
        int newEndTime = newStartTime + nodalWeight;//####[119]####
        processorArray[newProcessor] = newEndTime;//####[120]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[123]####
        return nextNode;//####[124]####
    }//####[125]####
}//####[125]####
