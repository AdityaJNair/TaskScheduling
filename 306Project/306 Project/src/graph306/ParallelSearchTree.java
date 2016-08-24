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
            if (UserOptions.getInstance().isVisible() && visualFrame != null) //####[40]####
            {//####[40]####
                visualFrame.notifyFirstGraph(currentNode);//####[41]####
            }//####[42]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[44]####
            {//####[44]####
                minimumTime = maxTimeAtPoint(currentNode);//####[47]####
                bestSchedule = currentNode.getCurrentPath();//####[48]####
                if (UserOptions.getInstance().isVisible() && visualFrame != null) //####[50]####
                {//####[50]####
                    visualFrame.notifyGraph(currentNode, minimumTime, endArray(bestSchedule));//####[51]####
                }//####[52]####
            }//####[53]####
            return;//####[54]####
        }//####[55]####
        if (minimumTime != Integer.MAX_VALUE) //####[58]####
        {//####[58]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[60]####
            {//####[60]####
                return;//####[61]####
            }//####[62]####
            if (calculateLowerBound(currentNode, nodesToCheck) >= minimumTime) //####[64]####
            {//####[64]####
                return;//####[65]####
            }//####[66]####
        }//####[67]####
        TaskID newTask = null;//####[69]####
        for (String nodeToCheckStr : nodesToCheck) //####[70]####
        {//####[70]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[71]####
            {//####[71]####
                int count = 0;//####[72]####
                for (int i = 0; i < numberofProcessors; i++) //####[73]####
                {//####[73]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[74]####
                    {//####[74]####
                        count++;//####[75]####
                    }//####[76]####
                }//####[77]####
                int killtree = 0;//####[78]####
                if (count >= 2) //####[79]####
                {//####[79]####
                    killtree = count - 1;//####[80]####
                }//####[81]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[82]####
                {//####[82]####
                    NodeObject newNode = createNextNode(currentNode, nodeToCheckStr, j);//####[83]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[86]####
                    newUpdatedListWithoutCurrentNode.remove(nodeToCheckStr);//####[87]####
                    nodeNumber++;//####[88]####
                    if (UserOptions.getInstance().isVisible() && visualFrame != null) //####[89]####
                    {//####[89]####
                        visualFrame.notifySecondGraph(nodeNumber);//####[90]####
                    }//####[91]####
                    if (isSubtask) //####[92]####
                    {//####[92]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, true);//####[93]####
                        continue;//####[94]####
                    }//####[95]####
                    if (semaphore == 0) //####[97]####
                    {//####[97]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, false);//####[98]####
                    } else {//####[99]####
                        semaphore--;//####[100]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[101]####
                        taskGroup.add(newTask);//####[103]####
                    }//####[104]####
                }//####[105]####
            }//####[106]####
        }//####[107]####
        if (isSubtask) //####[108]####
        {//####[108]####
            return;//####[109]####
        }//####[110]####
        try {//####[111]####
            taskGroup.waitTillFinished();//####[112]####
        } catch (Exception e) {//####[113]####
            e.printStackTrace();//####[114]####
        }//####[115]####
    }//####[116]####
//####[118]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[118]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            try {//####[118]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[118]####
                    NodeObject.class, List.class//####[118]####
                });//####[118]####
            } catch (Exception e) {//####[118]####
                e.printStackTrace();//####[118]####
            }//####[118]####
        }//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[118]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[118]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[118]####
        // ensure Method variable is set//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[118]####
        }//####[118]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[118]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[118]####
        taskinfo.setInstance(this);//####[118]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[118]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[118]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[118]####
        // ensure Method variable is set//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[118]####
        }//####[118]####
        taskinfo.setTaskIdArgIndexes(0);//####[118]####
        taskinfo.addDependsOn(currentNode);//####[118]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[118]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[118]####
        taskinfo.setInstance(this);//####[118]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[118]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[118]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[118]####
        // ensure Method variable is set//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[118]####
        }//####[118]####
        taskinfo.setQueueArgIndexes(0);//####[118]####
        taskinfo.setIsPipeline(true);//####[118]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[118]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[118]####
        taskinfo.setInstance(this);//####[118]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[118]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[118]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[118]####
        // ensure Method variable is set//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[118]####
        }//####[118]####
        taskinfo.setTaskIdArgIndexes(1);//####[118]####
        taskinfo.addDependsOn(nodesToCheck);//####[118]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[118]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[118]####
        taskinfo.setInstance(this);//####[118]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[118]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[118]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[118]####
        // ensure Method variable is set//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[118]####
        }//####[118]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[118]####
        taskinfo.addDependsOn(currentNode);//####[118]####
        taskinfo.addDependsOn(nodesToCheck);//####[118]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[118]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[118]####
        taskinfo.setInstance(this);//####[118]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[118]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[118]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[118]####
        // ensure Method variable is set//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[118]####
        }//####[118]####
        taskinfo.setQueueArgIndexes(0);//####[118]####
        taskinfo.setIsPipeline(true);//####[118]####
        taskinfo.setTaskIdArgIndexes(1);//####[118]####
        taskinfo.addDependsOn(nodesToCheck);//####[118]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[118]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[118]####
        taskinfo.setInstance(this);//####[118]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[118]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[118]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[118]####
        // ensure Method variable is set//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[118]####
        }//####[118]####
        taskinfo.setQueueArgIndexes(1);//####[118]####
        taskinfo.setIsPipeline(true);//####[118]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[118]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[118]####
        taskinfo.setInstance(this);//####[118]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[118]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[118]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[118]####
        // ensure Method variable is set//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[118]####
        }//####[118]####
        taskinfo.setQueueArgIndexes(1);//####[118]####
        taskinfo.setIsPipeline(true);//####[118]####
        taskinfo.setTaskIdArgIndexes(0);//####[118]####
        taskinfo.addDependsOn(currentNode);//####[118]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[118]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[118]####
        taskinfo.setInstance(this);//####[118]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[118]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[118]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[118]####
    }//####[118]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[118]####
        // ensure Method variable is set//####[118]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[118]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[118]####
        }//####[118]####
        taskinfo.setQueueArgIndexes(0, 1);//####[118]####
        taskinfo.setIsPipeline(true);//####[118]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[118]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[118]####
        taskinfo.setInstance(this);//####[118]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[118]####
    }//####[118]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[118]####
        recursiveMethod(currentNode, nodesToCheck, true);//####[119]####
        semaphore++;//####[120]####
    }//####[121]####
//####[121]####
//####[124]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[124]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[127]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[128]####
        String newNodeName = nodeToCheckStr;//####[131]####
        int newProcessor = processorNumber;//####[132]####
        int nodalWeight = getNodalWeight(newNodeName);//####[133]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[134]####
        int newEndTime = newStartTime + nodalWeight;//####[135]####
        processorArray[newProcessor] = newEndTime;//####[136]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[139]####
        return nextNode;//####[140]####
    }//####[141]####
}//####[141]####
