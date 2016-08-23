package graph306;//####[1]####
//####[1]####
import pt.queues.FifoLifoQueue;//####[2]####
import pt.runtime.*;//####[3]####
import java.util.ArrayList;//####[5]####
import java.util.Arrays;//####[6]####
import java.util.LinkedList;//####[7]####
import java.util.List;//####[8]####
//####[8]####
//-- ParaTask related imports//####[8]####
import pt.runtime.*;//####[8]####
import java.util.concurrent.ExecutionException;//####[8]####
import java.util.concurrent.locks.*;//####[8]####
import java.lang.reflect.*;//####[8]####
import pt.runtime.GuiThread;//####[8]####
import java.util.concurrent.BlockingQueue;//####[8]####
import java.util.ArrayList;//####[8]####
import java.util.List;//####[8]####
//####[8]####
public class Parallalism extends SolutionTree {//####[10]####
    static{ParaTask.init();}//####[10]####
    /*  ParaTask helper method to access private/protected slots *///####[10]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[10]####
        if (m.getParameterTypes().length == 0)//####[10]####
            m.invoke(instance);//####[10]####
        else if ((m.getParameterTypes().length == 1))//####[10]####
            m.invoke(instance, arg);//####[10]####
        else //####[10]####
            m.invoke(instance, arg, interResult);//####[10]####
    }//####[10]####
//####[12]####
    int semaphore = UserOptions.getInstance().getParallelThreads();//####[12]####
//####[13]####
    FifoLifoQueue<List<NodeObject>> bestScheduleThreaded = new FifoLifoQueue<List<NodeObject>>();//####[13]####
//####[14]####
    FifoLifoQueue<Integer> bestTimeThreaded = new FifoLifoQueue<Integer>();//####[14]####
//####[15]####
    TaskIDGroup taskGroup = new TaskIDGroup(UserOptions.getInstance().getParallelThreads() + 5);//####[15]####
//####[20]####
    /**
	 * @param inputGraph
	 *///####[20]####
    public Parallalism(AdjacencyList inputGraph) {//####[20]####
        super(inputGraph);//####[21]####
        bestTimeThreaded.addGlobal(Integer.MAX_VALUE);//####[22]####
    }//####[24]####
//####[26]####
    private static volatile Method __pt__parallel_NodeObject_ListString_method = null;//####[26]####
    private synchronized static void __pt__parallel_NodeObject_ListString_ensureMethodVarSet() {//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            try {//####[26]####
                __pt__parallel_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__parallel", new Class[] {//####[26]####
                    NodeObject.class, List.class//####[26]####
                });//####[26]####
            } catch (Exception e) {//####[26]####
                e.printStackTrace();//####[26]####
            }//####[26]####
        }//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(NodeObject currentNode, List<String> nodesToCheck) {//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[26]####
        // ensure Method variable is set//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[26]####
        }//####[26]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[26]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[26]####
        // ensure Method variable is set//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[26]####
        }//####[26]####
        taskinfo.setTaskIdArgIndexes(0);//####[26]####
        taskinfo.addDependsOn(currentNode);//####[26]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[26]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[26]####
        // ensure Method variable is set//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[26]####
        }//####[26]####
        taskinfo.setQueueArgIndexes(0);//####[26]####
        taskinfo.setIsPipeline(true);//####[26]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[26]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[26]####
        // ensure Method variable is set//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[26]####
        }//####[26]####
        taskinfo.setTaskIdArgIndexes(1);//####[26]####
        taskinfo.addDependsOn(nodesToCheck);//####[26]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[26]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[26]####
        // ensure Method variable is set//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[26]####
        }//####[26]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[26]####
        taskinfo.addDependsOn(currentNode);//####[26]####
        taskinfo.addDependsOn(nodesToCheck);//####[26]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[26]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[26]####
        // ensure Method variable is set//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[26]####
        }//####[26]####
        taskinfo.setQueueArgIndexes(0);//####[26]####
        taskinfo.setIsPipeline(true);//####[26]####
        taskinfo.setTaskIdArgIndexes(1);//####[26]####
        taskinfo.addDependsOn(nodesToCheck);//####[26]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[26]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[26]####
        // ensure Method variable is set//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[26]####
        }//####[26]####
        taskinfo.setQueueArgIndexes(1);//####[26]####
        taskinfo.setIsPipeline(true);//####[26]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[26]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[26]####
        // ensure Method variable is set//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[26]####
        }//####[26]####
        taskinfo.setQueueArgIndexes(1);//####[26]####
        taskinfo.setIsPipeline(true);//####[26]####
        taskinfo.setTaskIdArgIndexes(0);//####[26]####
        taskinfo.addDependsOn(currentNode);//####[26]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[26]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[26]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[26]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[26]####
    }//####[26]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[26]####
        // ensure Method variable is set//####[26]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[26]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[26]####
        }//####[26]####
        taskinfo.setQueueArgIndexes(0, 1);//####[26]####
        taskinfo.setIsPipeline(true);//####[26]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[26]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[26]####
        taskinfo.setInstance(this);//####[26]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[26]####
    }//####[26]####
    public void __pt__parallel(NodeObject currentNode, List<String> nodesToCheck) {//####[26]####
        calculateTime(currentNode, nodesToCheck);//####[27]####
        return;//####[28]####
    }//####[29]####
//####[29]####
//####[38]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[38]####
    public void calculateTime(NodeObject currentNode, List<String> nodesToCheck, boolean isThreaded) {//####[38]####
        if (nodesToCheck.size() == 0) //####[40]####
        {//####[40]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[43]####
            {//####[43]####
                minimumTime = maxTimeAtPoint(currentNode);//####[46]####
                bestSchedule = currentNode.getCurrentPath();//####[47]####
            }//####[48]####
            return;//####[49]####
        }//####[50]####
        if (minimumTime != Integer.MAX_VALUE) //####[52]####
        {//####[52]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[54]####
            {//####[54]####
                return;//####[55]####
            }//####[56]####
        }//####[61]####
        if (!isThreaded) //####[62]####
        {//####[62]####
            calculateTime(currentNode, nodesToCheck);//####[63]####
            return;//####[64]####
        }//####[65]####
        List<String> sources = inputGraph.getSourceNodes();//####[66]####
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
            } else {//####[96]####
                calculateTime(currentNode, nodesToCheck);//####[97]####
            }//####[98]####
        }//####[99]####
        try {//####[101]####
            taskGroup.waitTillFinished();//####[102]####
            System.out.println("Not waiting anymore");//####[103]####
        } catch (Exception e) {//####[104]####
            e.printStackTrace();//####[105]####
        }//####[106]####
    }//####[109]####
//####[112]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_boolean_method = null;//####[112]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet() {//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            try {//####[112]####
                __pt__intermediateMethod_NodeObject_ListString_boolean_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[112]####
                    NodeObject.class, List.class, boolean.class//####[112]####
                });//####[112]####
            } catch (Exception e) {//####[112]####
                e.printStackTrace();//####[112]####
            }//####[112]####
        }//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(0);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(1);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(1);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(1);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(1);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(0);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0, 1);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(2);//####[112]####
        taskinfo.addDependsOn(isHeadThread);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.addDependsOn(isHeadThread);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(2);//####[112]####
        taskinfo.addDependsOn(isHeadThread);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.addDependsOn(isHeadThread);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setTaskIdArgIndexes(0, 1, 2);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.addDependsOn(isHeadThread);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.addDependsOn(isHeadThread);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(1);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(2);//####[112]####
        taskinfo.addDependsOn(isHeadThread);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(1);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.addDependsOn(isHeadThread);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0, 1);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(2);//####[112]####
        taskinfo.addDependsOn(isHeadThread);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(2);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(2);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(0);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0, 2);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(2);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(1);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(2);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0, 2);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(1);//####[112]####
        taskinfo.addDependsOn(nodesToCheck);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(1, 2);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(1, 2);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setTaskIdArgIndexes(0);//####[112]####
        taskinfo.addDependsOn(currentNode);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[112]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[112]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[112]####
    }//####[112]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[112]####
        // ensure Method variable is set//####[112]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[112]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[112]####
        }//####[112]####
        taskinfo.setQueueArgIndexes(0, 1, 2);//####[112]####
        taskinfo.setIsPipeline(true);//####[112]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[112]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[112]####
        taskinfo.setInstance(this);//####[112]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[112]####
    }//####[112]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[112]####
        calculateTimeParallel(currentNode, nodesToCheck, isHeadThread);//####[113]####
    }//####[114]####
//####[114]####
//####[124]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[124]####
    public void calculateTimeParallel(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[124]####
        if (nodesToCheck.size() == 0) //####[132]####
        {//####[132]####
            if (maxTimeAtPoint(currentNode) < getMinimumTime()) //####[135]####
            {//####[135]####
                setMinimumTime(maxTimeAtPoint(currentNode));//####[138]####
                setBestSchedule(currentNode.getCurrentPath());//####[139]####
            }//####[140]####
            if (isHeadThread) //####[141]####
            {//####[141]####
                semaphore++;//####[142]####
            }//####[143]####
            return;//####[144]####
        }//####[145]####
        if (minimumTime != Integer.MAX_VALUE) //####[147]####
        {//####[147]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[149]####
            {//####[149]####
                if (isHeadThread) //####[150]####
                {//####[150]####
                    semaphore++;//####[151]####
                }//####[152]####
                return;//####[153]####
            }//####[154]####
        }//####[159]####
        for (String nodeToCheckStr : nodesToCheck) //####[163]####
        {//####[163]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[164]####
            {//####[164]####
                int count = 0;//####[165]####
                for (int i = 0; i < numberofProcessors; i++) //####[166]####
                {//####[166]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[167]####
                    {//####[167]####
                        count++;//####[168]####
                    }//####[169]####
                }//####[170]####
                int killtree = 0;//####[171]####
                if (count >= 2) //####[172]####
                {//####[172]####
                    killtree = count - 1;//####[173]####
                }//####[174]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[175]####
                {//####[175]####
                    ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[181]####
                    int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[182]####
                    String newNodeName = nodeToCheckStr;//####[185]####
                    int newProcessor = j;//####[186]####
                    int nodalWeight = getNodalWeight(newNodeName);//####[187]####
                    int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[188]####
                    int newEndTime = newStartTime + nodalWeight;//####[189]####
                    processorArray[newProcessor] = newEndTime;//####[190]####
                    NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[193]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[195]####
                    newUpdatedListWithoutCurrentNode.remove(newNodeName);//####[196]####
                    nodeNumber++;//####[197]####
                    if (semaphore > 0) //####[199]####
                    {//####[199]####
                        semaphore--;//####[200]####
                        TaskID newTask = intermediateMethod(nextNode, newUpdatedListWithoutCurrentNode, true);//####[201]####
                        taskGroup.add(newTask);//####[202]####
                    } else {//####[204]####
                        calculateTimeParallel(nextNode, newUpdatedListWithoutCurrentNode, false);//####[206]####
                    }//####[207]####
                }//####[208]####
            }//####[209]####
        }//####[210]####
        if (isHeadThread) //####[211]####
        {//####[211]####
            semaphore++;//####[212]####
        }//####[213]####
    }//####[214]####
}//####[214]####
