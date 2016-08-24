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
    TaskIDGroup taskGroup = new TaskIDGroup(UserOptions.getInstance().getParallelThreads() + 5);//####[19]####
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
                    }//####[63]####
                }//####[64]####
                int killtree = 0;//####[65]####
                if (count >= 2) //####[66]####
                {//####[66]####
                    killtree = count - 1;//####[67]####
                }//####[68]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[69]####
                {//####[69]####
                    NodeObject newNode = createNextNode(currentNode, nodeToCheckStr, j);//####[70]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[73]####
                    newUpdatedListWithoutCurrentNode.remove(nodeToCheckStr);//####[74]####
                    nodeNumber++;//####[75]####
                    if (isSubtask) //####[76]####
                    {//####[76]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, true);//####[77]####
                        continue;//####[78]####
                    }//####[79]####
                    if (semaphore == 0) //####[81]####
                    {//####[81]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, false);//####[82]####
                    } else {//####[83]####
                        semaphore--;//####[84]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[85]####
                        taskGroup.add(newTask);//####[87]####
                    }//####[88]####
                }//####[89]####
            }//####[90]####
        }//####[91]####
        if (isSubtask) //####[92]####
        {//####[92]####
            return;//####[93]####
        }//####[94]####
        try {//####[95]####
            taskGroup.waitTillFinished();//####[96]####
        } catch (Exception e) {//####[97]####
            e.printStackTrace();//####[98]####
        }//####[99]####
    }//####[100]####
//####[102]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[102]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            try {//####[102]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[102]####
                    NodeObject.class, List.class//####[102]####
                });//####[102]####
            } catch (Exception e) {//####[102]####
                e.printStackTrace();//####[102]####
            }//####[102]####
        }//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[102]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[102]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[102]####
        // ensure Method variable is set//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[102]####
        }//####[102]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[102]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[102]####
        taskinfo.setInstance(this);//####[102]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[102]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[102]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[102]####
        // ensure Method variable is set//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[102]####
        }//####[102]####
        taskinfo.setTaskIdArgIndexes(0);//####[102]####
        taskinfo.addDependsOn(currentNode);//####[102]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[102]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[102]####
        taskinfo.setInstance(this);//####[102]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[102]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[102]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[102]####
        // ensure Method variable is set//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[102]####
        }//####[102]####
        taskinfo.setQueueArgIndexes(0);//####[102]####
        taskinfo.setIsPipeline(true);//####[102]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[102]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[102]####
        taskinfo.setInstance(this);//####[102]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[102]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[102]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[102]####
        // ensure Method variable is set//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[102]####
        }//####[102]####
        taskinfo.setTaskIdArgIndexes(1);//####[102]####
        taskinfo.addDependsOn(nodesToCheck);//####[102]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[102]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[102]####
        taskinfo.setInstance(this);//####[102]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[102]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[102]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[102]####
        // ensure Method variable is set//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[102]####
        }//####[102]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[102]####
        taskinfo.addDependsOn(currentNode);//####[102]####
        taskinfo.addDependsOn(nodesToCheck);//####[102]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[102]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[102]####
        taskinfo.setInstance(this);//####[102]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[102]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[102]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[102]####
        // ensure Method variable is set//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[102]####
        }//####[102]####
        taskinfo.setQueueArgIndexes(0);//####[102]####
        taskinfo.setIsPipeline(true);//####[102]####
        taskinfo.setTaskIdArgIndexes(1);//####[102]####
        taskinfo.addDependsOn(nodesToCheck);//####[102]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[102]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[102]####
        taskinfo.setInstance(this);//####[102]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[102]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[102]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[102]####
        // ensure Method variable is set//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[102]####
        }//####[102]####
        taskinfo.setQueueArgIndexes(1);//####[102]####
        taskinfo.setIsPipeline(true);//####[102]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[102]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[102]####
        taskinfo.setInstance(this);//####[102]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[102]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[102]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[102]####
        // ensure Method variable is set//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[102]####
        }//####[102]####
        taskinfo.setQueueArgIndexes(1);//####[102]####
        taskinfo.setIsPipeline(true);//####[102]####
        taskinfo.setTaskIdArgIndexes(0);//####[102]####
        taskinfo.addDependsOn(currentNode);//####[102]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[102]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[102]####
        taskinfo.setInstance(this);//####[102]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[102]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[102]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[102]####
    }//####[102]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[102]####
        // ensure Method variable is set//####[102]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[102]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[102]####
        }//####[102]####
        taskinfo.setQueueArgIndexes(0, 1);//####[102]####
        taskinfo.setIsPipeline(true);//####[102]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[102]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[102]####
        taskinfo.setInstance(this);//####[102]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[102]####
    }//####[102]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[102]####
        recursiveMethod(currentNode, nodesToCheck, true);//####[103]####
        semaphore++;//####[104]####
    }//####[105]####
//####[105]####
//####[107]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[107]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[110]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[111]####
        String newNodeName = nodeToCheckStr;//####[114]####
        int newProcessor = processorNumber;//####[115]####
        int nodalWeight = getNodalWeight(newNodeName);//####[116]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[117]####
        int newEndTime = newStartTime + nodalWeight;//####[118]####
        processorArray[newProcessor] = newEndTime;//####[119]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[122]####
        return nextNode;//####[123]####
    }//####[124]####
}//####[124]####
