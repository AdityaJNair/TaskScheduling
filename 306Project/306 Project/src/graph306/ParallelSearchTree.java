package graph306;//####[1]####
//####[1]####
import pt.queues.FifoLifoQueue;//####[2]####
import pt.runtime.*;//####[3]####
import java.util.ArrayList;//####[5]####
import java.util.Arrays;//####[6]####
import java.util.LinkedList;//####[7]####
import java.util.List;//####[8]####
import data_structures.*;//####[9]####
//####[9]####
//-- ParaTask related imports//####[9]####
import pt.runtime.*;//####[9]####
import java.util.concurrent.ExecutionException;//####[9]####
import java.util.concurrent.locks.*;//####[9]####
import java.lang.reflect.*;//####[9]####
import pt.runtime.GuiThread;//####[9]####
import java.util.concurrent.BlockingQueue;//####[9]####
import java.util.ArrayList;//####[9]####
import java.util.List;//####[9]####
//####[9]####
public class ParallelSearchTree extends SolutionTree {//####[11]####
    static{ParaTask.init();}//####[11]####
    /*  ParaTask helper method to access private/protected slots *///####[11]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[11]####
        if (m.getParameterTypes().length == 0)//####[11]####
            m.invoke(instance);//####[11]####
        else if ((m.getParameterTypes().length == 1))//####[11]####
            m.invoke(instance, arg);//####[11]####
        else //####[11]####
            m.invoke(instance, arg, interResult);//####[11]####
    }//####[11]####
//####[13]####
    int semaphore = UserOptions.getInstance().getParallelThreads();//####[13]####
//####[14]####
    FifoLifoQueue<List<NodeObject>> bestScheduleThreaded = new FifoLifoQueue<List<NodeObject>>();//####[14]####
//####[15]####
    FifoLifoQueue<Integer> bestTimeThreaded = new FifoLifoQueue<Integer>();//####[15]####
//####[16]####
    List<String> sourceNodes = inputGraph.getSourceNodes();//####[16]####
//####[17]####
    TaskIDGroup taskGroup = new TaskIDGroup(UserOptions.getInstance().getParallelThreads() + 5);//####[17]####
//####[21]####
    /**
	 * @param inputGraph
	 *///####[21]####
    public ParallelSearchTree(AdjacencyList inputGraph) {//####[21]####
        super(inputGraph);//####[22]####
        bestTimeThreaded.addGlobal(Integer.MAX_VALUE);//####[23]####
    }//####[25]####
//####[27]####
    public void recursiveMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isSubtask) {//####[27]####
        if (nodesToCheck.size() == 0) //####[30]####
        {//####[30]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[33]####
            {//####[33]####
                minimumTime = maxTimeAtPoint(currentNode);//####[36]####
                bestSchedule = currentNode.getCurrentPath();//####[37]####
            }//####[38]####
            return;//####[39]####
        }//####[40]####
        if (minimumTime != Integer.MAX_VALUE) //####[43]####
        {//####[43]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[45]####
            {//####[45]####
                return;//####[46]####
            }//####[47]####
            if (calculateLowerBound(currentNode, nodesToCheck) >= minimumTime) //####[49]####
            {//####[49]####
                return;//####[50]####
            }//####[51]####
        }//####[52]####
        TaskID newTask = null;//####[54]####
        for (String nodeToCheckStr : nodesToCheck) //####[55]####
        {//####[55]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[56]####
            {//####[56]####
                int count = 0;//####[57]####
                for (int i = 0; i < numberofProcessors; i++) //####[58]####
                {//####[58]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[59]####
                    {//####[59]####
                        count++;//####[60]####
                    }//####[61]####
                }//####[62]####
                int killtree = 0;//####[63]####
                if (count >= 2) //####[64]####
                {//####[64]####
                    killtree = count - 1;//####[65]####
                }//####[66]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[67]####
                {//####[67]####
                    NodeObject newNode = createNextNode(currentNode, nodeToCheckStr, j);//####[68]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[71]####
                    newUpdatedListWithoutCurrentNode.remove(nodeToCheckStr);//####[72]####
                    nodeNumber++;//####[73]####
                    if (isSubtask) //####[75]####
                    {//####[75]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, true);//####[76]####
                        continue;//####[77]####
                    }//####[78]####
                    if (semaphore == 0) //####[80]####
                    {//####[80]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, false);//####[81]####
                    } else {//####[82]####
                        semaphore--;//####[83]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[84]####
                        taskGroup.add(newTask);//####[86]####
                    }//####[87]####
                }//####[88]####
            }//####[89]####
        }//####[90]####
        if (isSubtask) //####[91]####
        {//####[91]####
            return;//####[92]####
        }//####[93]####
        try {//####[94]####
            taskGroup.waitTillFinished();//####[95]####
        } catch (Exception e) {//####[96]####
            e.printStackTrace();//####[97]####
        }//####[98]####
    }//####[99]####
//####[101]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[101]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            try {//####[101]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[101]####
                    NodeObject.class, List.class//####[101]####
                });//####[101]####
            } catch (Exception e) {//####[101]####
                e.printStackTrace();//####[101]####
            }//####[101]####
        }//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[101]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setTaskIdArgIndexes(0);//####[101]####
        taskinfo.addDependsOn(currentNode);//####[101]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[101]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setQueueArgIndexes(0);//####[101]####
        taskinfo.setIsPipeline(true);//####[101]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[101]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setTaskIdArgIndexes(1);//####[101]####
        taskinfo.addDependsOn(nodesToCheck);//####[101]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[101]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[101]####
        taskinfo.addDependsOn(currentNode);//####[101]####
        taskinfo.addDependsOn(nodesToCheck);//####[101]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[101]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setQueueArgIndexes(0);//####[101]####
        taskinfo.setIsPipeline(true);//####[101]####
        taskinfo.setTaskIdArgIndexes(1);//####[101]####
        taskinfo.addDependsOn(nodesToCheck);//####[101]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[101]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setQueueArgIndexes(1);//####[101]####
        taskinfo.setIsPipeline(true);//####[101]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[101]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setQueueArgIndexes(1);//####[101]####
        taskinfo.setIsPipeline(true);//####[101]####
        taskinfo.setTaskIdArgIndexes(0);//####[101]####
        taskinfo.addDependsOn(currentNode);//####[101]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[101]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[101]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[101]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[101]####
    }//####[101]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[101]####
        // ensure Method variable is set//####[101]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[101]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[101]####
        }//####[101]####
        taskinfo.setQueueArgIndexes(0, 1);//####[101]####
        taskinfo.setIsPipeline(true);//####[101]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[101]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[101]####
        taskinfo.setInstance(this);//####[101]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[101]####
    }//####[101]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[101]####
        recursiveMethod(currentNode, nodesToCheck, true);//####[102]####
        semaphore++;//####[103]####
    }//####[104]####
//####[104]####
//####[106]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[106]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[109]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[110]####
        String newNodeName = nodeToCheckStr;//####[113]####
        int newProcessor = processorNumber;//####[114]####
        int nodalWeight = getNodalWeight(newNodeName);//####[115]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[116]####
        int newEndTime = newStartTime + nodalWeight;//####[117]####
        processorArray[newProcessor] = newEndTime;//####[118]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[121]####
        return nextNode;//####[122]####
    }//####[123]####
}//####[123]####
