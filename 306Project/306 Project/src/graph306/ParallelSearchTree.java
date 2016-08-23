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
    public void recursiveMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[27]####
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
                    if (semaphore == 0) //####[75]####
                    {//####[75]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode);//####[76]####
                    } else {//####[77]####
                        semaphore--;//####[78]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[79]####
                        taskGroup.add(newTask);//####[81]####
                    }//####[82]####
                }//####[83]####
            }//####[84]####
        }//####[85]####
        try {//####[86]####
            taskGroup.waitTillFinished();//####[87]####
            System.out.println("Not waiting anymore");//####[88]####
        } catch (Exception e) {//####[89]####
            e.printStackTrace();//####[90]####
        }//####[91]####
    }//####[92]####
//####[94]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[94]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            try {//####[94]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[94]####
                    NodeObject.class, List.class//####[94]####
                });//####[94]####
            } catch (Exception e) {//####[94]####
                e.printStackTrace();//####[94]####
            }//####[94]####
        }//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[94]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[94]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[94]####
        // ensure Method variable is set//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[94]####
        }//####[94]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[94]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[94]####
        taskinfo.setInstance(this);//####[94]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[94]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[94]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[94]####
        // ensure Method variable is set//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[94]####
        }//####[94]####
        taskinfo.setTaskIdArgIndexes(0);//####[94]####
        taskinfo.addDependsOn(currentNode);//####[94]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[94]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[94]####
        taskinfo.setInstance(this);//####[94]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[94]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[94]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[94]####
        // ensure Method variable is set//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[94]####
        }//####[94]####
        taskinfo.setQueueArgIndexes(0);//####[94]####
        taskinfo.setIsPipeline(true);//####[94]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[94]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[94]####
        taskinfo.setInstance(this);//####[94]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[94]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[94]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[94]####
        // ensure Method variable is set//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[94]####
        }//####[94]####
        taskinfo.setTaskIdArgIndexes(1);//####[94]####
        taskinfo.addDependsOn(nodesToCheck);//####[94]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[94]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[94]####
        taskinfo.setInstance(this);//####[94]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[94]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[94]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[94]####
        // ensure Method variable is set//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[94]####
        }//####[94]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[94]####
        taskinfo.addDependsOn(currentNode);//####[94]####
        taskinfo.addDependsOn(nodesToCheck);//####[94]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[94]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[94]####
        taskinfo.setInstance(this);//####[94]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[94]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[94]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[94]####
        // ensure Method variable is set//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[94]####
        }//####[94]####
        taskinfo.setQueueArgIndexes(0);//####[94]####
        taskinfo.setIsPipeline(true);//####[94]####
        taskinfo.setTaskIdArgIndexes(1);//####[94]####
        taskinfo.addDependsOn(nodesToCheck);//####[94]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[94]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[94]####
        taskinfo.setInstance(this);//####[94]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[94]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[94]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[94]####
        // ensure Method variable is set//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[94]####
        }//####[94]####
        taskinfo.setQueueArgIndexes(1);//####[94]####
        taskinfo.setIsPipeline(true);//####[94]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[94]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[94]####
        taskinfo.setInstance(this);//####[94]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[94]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[94]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[94]####
        // ensure Method variable is set//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[94]####
        }//####[94]####
        taskinfo.setQueueArgIndexes(1);//####[94]####
        taskinfo.setIsPipeline(true);//####[94]####
        taskinfo.setTaskIdArgIndexes(0);//####[94]####
        taskinfo.addDependsOn(currentNode);//####[94]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[94]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[94]####
        taskinfo.setInstance(this);//####[94]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[94]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[94]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[94]####
    }//####[94]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[94]####
        // ensure Method variable is set//####[94]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[94]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[94]####
        }//####[94]####
        taskinfo.setQueueArgIndexes(0, 1);//####[94]####
        taskinfo.setIsPipeline(true);//####[94]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[94]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[94]####
        taskinfo.setInstance(this);//####[94]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[94]####
    }//####[94]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[94]####
        System.out.println(CurrentTask.getProgress());//####[95]####
        System.out.println("Created a new thread: " + currentThreadID());//####[96]####
        recursiveMethod(currentNode, nodesToCheck);//####[98]####
        semaphore++;//####[99]####
    }//####[100]####
//####[100]####
//####[102]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[102]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[105]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[106]####
        String newNodeName = nodeToCheckStr;//####[109]####
        int newProcessor = processorNumber;//####[110]####
        int nodalWeight = getNodalWeight(newNodeName);//####[111]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[112]####
        int newEndTime = newStartTime + nodalWeight;//####[113]####
        processorArray[newProcessor] = newEndTime;//####[114]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[117]####
        return nextNode;//####[118]####
    }//####[119]####
}//####[119]####
