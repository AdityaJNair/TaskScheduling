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
public class Parallalism extends SolutionTree {//####[11]####
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
    TaskIDGroup taskGroup = new TaskIDGroup(UserOptions.getInstance().getParallelThreads() + 5);//####[16]####
//####[21]####
    /**
	 * @param inputGraph
	 *///####[21]####
    public Parallalism(AdjacencyList inputGraph) {//####[21]####
        super(inputGraph);//####[22]####
        bestTimeThreaded.addGlobal(Integer.MAX_VALUE);//####[23]####
    }//####[25]####
//####[27]####
    private static volatile Method __pt__parallel_NodeObject_ListString_method = null;//####[27]####
    private synchronized static void __pt__parallel_NodeObject_ListString_ensureMethodVarSet() {//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            try {//####[27]####
                __pt__parallel_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__parallel", new Class[] {//####[27]####
                    NodeObject.class, List.class//####[27]####
                });//####[27]####
            } catch (Exception e) {//####[27]####
                e.printStackTrace();//####[27]####
            }//####[27]####
        }//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(NodeObject currentNode, List<String> nodesToCheck) {//####[27]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[27]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[27]####
        // ensure Method variable is set//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[27]####
        }//####[27]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[27]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[27]####
        taskinfo.setInstance(this);//####[27]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[27]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[27]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[27]####
        // ensure Method variable is set//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[27]####
        }//####[27]####
        taskinfo.setTaskIdArgIndexes(0);//####[27]####
        taskinfo.addDependsOn(currentNode);//####[27]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[27]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[27]####
        taskinfo.setInstance(this);//####[27]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[27]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[27]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[27]####
        // ensure Method variable is set//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[27]####
        }//####[27]####
        taskinfo.setQueueArgIndexes(0);//####[27]####
        taskinfo.setIsPipeline(true);//####[27]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[27]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[27]####
        taskinfo.setInstance(this);//####[27]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[27]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[27]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[27]####
        // ensure Method variable is set//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[27]####
        }//####[27]####
        taskinfo.setTaskIdArgIndexes(1);//####[27]####
        taskinfo.addDependsOn(nodesToCheck);//####[27]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[27]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[27]####
        taskinfo.setInstance(this);//####[27]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[27]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[27]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[27]####
        // ensure Method variable is set//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[27]####
        }//####[27]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[27]####
        taskinfo.addDependsOn(currentNode);//####[27]####
        taskinfo.addDependsOn(nodesToCheck);//####[27]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[27]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[27]####
        taskinfo.setInstance(this);//####[27]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[27]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[27]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[27]####
        // ensure Method variable is set//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[27]####
        }//####[27]####
        taskinfo.setQueueArgIndexes(0);//####[27]####
        taskinfo.setIsPipeline(true);//####[27]####
        taskinfo.setTaskIdArgIndexes(1);//####[27]####
        taskinfo.addDependsOn(nodesToCheck);//####[27]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[27]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[27]####
        taskinfo.setInstance(this);//####[27]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[27]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[27]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[27]####
        // ensure Method variable is set//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[27]####
        }//####[27]####
        taskinfo.setQueueArgIndexes(1);//####[27]####
        taskinfo.setIsPipeline(true);//####[27]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[27]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[27]####
        taskinfo.setInstance(this);//####[27]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[27]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[27]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[27]####
        // ensure Method variable is set//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[27]####
        }//####[27]####
        taskinfo.setQueueArgIndexes(1);//####[27]####
        taskinfo.setIsPipeline(true);//####[27]####
        taskinfo.setTaskIdArgIndexes(0);//####[27]####
        taskinfo.addDependsOn(currentNode);//####[27]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[27]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[27]####
        taskinfo.setInstance(this);//####[27]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[27]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[27]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[27]####
    }//####[27]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[27]####
        // ensure Method variable is set//####[27]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[27]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[27]####
        }//####[27]####
        taskinfo.setQueueArgIndexes(0, 1);//####[27]####
        taskinfo.setIsPipeline(true);//####[27]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[27]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[27]####
        taskinfo.setInstance(this);//####[27]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[27]####
    }//####[27]####
    public void __pt__parallel(NodeObject currentNode, List<String> nodesToCheck) {//####[27]####
        calculateTime(currentNode, nodesToCheck);//####[28]####
        return;//####[29]####
    }//####[30]####
//####[30]####
//####[39]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[39]####
    public void calculateTime(NodeObject currentNode, List<String> nodesToCheck, boolean isThreaded) {//####[39]####
        if (nodesToCheck.size() == 0) //####[41]####
        {//####[41]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[44]####
            {//####[44]####
                minimumTime = maxTimeAtPoint(currentNode);//####[47]####
                bestSchedule = currentNode.getCurrentPath();//####[48]####
            }//####[49]####
            return;//####[50]####
        }//####[51]####
        if (minimumTime != Integer.MAX_VALUE) //####[53]####
        {//####[53]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[55]####
            {//####[55]####
                return;//####[56]####
            }//####[57]####
        }//####[62]####
        if (!isThreaded) //####[63]####
        {//####[63]####
            calculateTime(currentNode, nodesToCheck);//####[64]####
            return;//####[65]####
        }//####[66]####
        for (String nodeToCheckStr : nodesToCheck) //####[68]####
        {//####[68]####
            ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[72]####
            int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[73]####
            String newNodeName = nodeToCheckStr;//####[76]####
            int newProcessor = 0;//####[77]####
            int nodalWeight = getNodalWeight(newNodeName);//####[78]####
            int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[79]####
            int newEndTime = newStartTime + nodalWeight;//####[80]####
            processorArray[newProcessor] = newEndTime;//####[81]####
            NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[84]####
            List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[86]####
            newUpdatedListWithoutCurrentNode.remove(newNodeName);//####[87]####
            nodeNumber++;//####[88]####
            if (semaphore > 0) //####[90]####
            {//####[90]####
                System.out.println("Creating thread number " + (semaphore));//####[91]####
                semaphore--;//####[92]####
                boolean isHeadThread = true;//####[93]####
                TaskID<Void> newTask = intermediateMethod(currentNode, nodesToCheck, true);//####[94]####
                taskGroup.add(newTask);//####[95]####
                return;//####[96]####
            } else {//####[97]####
                calculateTime(currentNode, nodesToCheck);//####[98]####
            }//####[99]####
        }//####[100]####
        try {//####[102]####
            taskGroup.waitTillFinished();//####[103]####
            System.out.println("Not waiting anymore");//####[104]####
        } catch (Exception e) {//####[105]####
            e.printStackTrace();//####[106]####
        }//####[107]####
    }//####[110]####
//####[113]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_boolean_method = null;//####[113]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet() {//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            try {//####[113]####
                __pt__intermediateMethod_NodeObject_ListString_boolean_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[113]####
                    NodeObject.class, List.class, boolean.class//####[113]####
                });//####[113]####
            } catch (Exception e) {//####[113]####
                e.printStackTrace();//####[113]####
            }//####[113]####
        }//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setTaskIdArgIndexes(0);//####[113]####
        taskinfo.addDependsOn(currentNode);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(0);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setTaskIdArgIndexes(1);//####[113]####
        taskinfo.addDependsOn(nodesToCheck);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[113]####
        taskinfo.addDependsOn(currentNode);//####[113]####
        taskinfo.addDependsOn(nodesToCheck);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(0);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(1);//####[113]####
        taskinfo.addDependsOn(nodesToCheck);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(1);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(1);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(0);//####[113]####
        taskinfo.addDependsOn(currentNode);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(0, 1);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setTaskIdArgIndexes(2);//####[113]####
        taskinfo.addDependsOn(isHeadThread);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[113]####
        taskinfo.addDependsOn(currentNode);//####[113]####
        taskinfo.addDependsOn(isHeadThread);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(0);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(2);//####[113]####
        taskinfo.addDependsOn(isHeadThread);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[113]####
        taskinfo.addDependsOn(nodesToCheck);//####[113]####
        taskinfo.addDependsOn(isHeadThread);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setTaskIdArgIndexes(0, 1, 2);//####[113]####
        taskinfo.addDependsOn(currentNode);//####[113]####
        taskinfo.addDependsOn(nodesToCheck);//####[113]####
        taskinfo.addDependsOn(isHeadThread);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(0);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[113]####
        taskinfo.addDependsOn(nodesToCheck);//####[113]####
        taskinfo.addDependsOn(isHeadThread);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(1);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(2);//####[113]####
        taskinfo.addDependsOn(isHeadThread);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(1);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[113]####
        taskinfo.addDependsOn(currentNode);//####[113]####
        taskinfo.addDependsOn(isHeadThread);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(0, 1);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(2);//####[113]####
        taskinfo.addDependsOn(isHeadThread);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(2);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(2);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(0);//####[113]####
        taskinfo.addDependsOn(currentNode);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(0, 2);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(2);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(1);//####[113]####
        taskinfo.addDependsOn(nodesToCheck);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(2);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[113]####
        taskinfo.addDependsOn(currentNode);//####[113]####
        taskinfo.addDependsOn(nodesToCheck);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(0, 2);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(1);//####[113]####
        taskinfo.addDependsOn(nodesToCheck);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(1, 2);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(1, 2);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setTaskIdArgIndexes(0);//####[113]####
        taskinfo.addDependsOn(currentNode);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[113]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[113]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[113]####
    }//####[113]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[113]####
        // ensure Method variable is set//####[113]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[113]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[113]####
        }//####[113]####
        taskinfo.setQueueArgIndexes(0, 1, 2);//####[113]####
        taskinfo.setIsPipeline(true);//####[113]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[113]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[113]####
        taskinfo.setInstance(this);//####[113]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[113]####
    }//####[113]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[113]####
        calculateTimeParallel(currentNode, nodesToCheck, isHeadThread);//####[114]####
    }//####[115]####
//####[115]####
//####[125]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[125]####
    public void calculateTimeParallel(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[125]####
        if (nodesToCheck.size() == 0) //####[133]####
        {//####[133]####
            if (maxTimeAtPoint(currentNode) < getMinimumTime()) //####[136]####
            {//####[136]####
                setMinimumTime(maxTimeAtPoint(currentNode));//####[139]####
                setBestSchedule(currentNode.getCurrentPath());//####[140]####
            }//####[141]####
            if (isHeadThread) //####[142]####
            {//####[142]####
                semaphore++;//####[143]####
            }//####[144]####
            return;//####[145]####
        }//####[146]####
        if (minimumTime != Integer.MAX_VALUE) //####[148]####
        {//####[148]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[150]####
            {//####[150]####
                if (isHeadThread) //####[151]####
                {//####[151]####
                    semaphore++;//####[152]####
                }//####[153]####
                return;//####[154]####
            }//####[155]####
        }//####[160]####
        for (String nodeToCheckStr : nodesToCheck) //####[164]####
        {//####[164]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[165]####
            {//####[165]####
                int count = 0;//####[166]####
                for (int i = 0; i < numberofProcessors; i++) //####[167]####
                {//####[167]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[168]####
                    {//####[168]####
                        count++;//####[169]####
                    }//####[170]####
                }//####[171]####
                int killtree = 0;//####[172]####
                if (count >= 2) //####[173]####
                {//####[173]####
                    killtree = count - 1;//####[174]####
                }//####[175]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[176]####
                {//####[176]####
                    ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[182]####
                    int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[183]####
                    String newNodeName = nodeToCheckStr;//####[186]####
                    int newProcessor = j;//####[187]####
                    int nodalWeight = getNodalWeight(newNodeName);//####[188]####
                    int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[189]####
                    int newEndTime = newStartTime + nodalWeight;//####[190]####
                    processorArray[newProcessor] = newEndTime;//####[191]####
                    NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[194]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[196]####
                    newUpdatedListWithoutCurrentNode.remove(newNodeName);//####[197]####
                    nodeNumber++;//####[198]####
                    if (semaphore > 0) //####[200]####
                    {//####[200]####
                        semaphore--;//####[201]####
                        TaskID newTask = intermediateMethod(nextNode, newUpdatedListWithoutCurrentNode, true);//####[202]####
                        taskGroup.add(newTask);//####[203]####
                    } else {//####[205]####
                        calculateTimeParallel(nextNode, newUpdatedListWithoutCurrentNode, false);//####[207]####
                    }//####[208]####
                }//####[209]####
            }//####[210]####
        }//####[211]####
        if (isHeadThread) //####[212]####
        {//####[212]####
            semaphore++;//####[213]####
        }//####[214]####
    }//####[215]####
}//####[215]####
