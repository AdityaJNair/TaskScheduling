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
        int numberOfSources = sources.size();//####[67]####
        for (String nodeToCheckStr : nodesToCheck) //####[70]####
        {//####[70]####
            ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[74]####
            int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[75]####
            String newNodeName = nodeToCheckStr;//####[78]####
            int newProcessor = 0;//####[79]####
            int nodalWeight = getNodalWeight(newNodeName);//####[80]####
            int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[81]####
            int newEndTime = newStartTime + nodalWeight;//####[82]####
            processorArray[newProcessor] = newEndTime;//####[83]####
            NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[86]####
            List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[88]####
            newUpdatedListWithoutCurrentNode.remove(newNodeName);//####[89]####
            nodeNumber++;//####[90]####
            if (semaphore > 0) //####[92]####
            {//####[92]####
                System.out.println("Creating thread number " + (semaphore));//####[93]####
                semaphore--;//####[94]####
                boolean isHeadThread = true;//####[95]####
                TaskID<Void> newTask = intermediateMethod(currentNode, nodesToCheck, true);//####[96]####
                taskGroup.add(newTask);//####[97]####
            } else {//####[98]####
                calculateTime(currentNode, nodesToCheck);//####[99]####
            }//####[100]####
        }//####[101]####
        try {//####[103]####
            taskGroup.waitTillFinished();//####[104]####
            System.out.println("Not waiting anymore");//####[105]####
        } catch (Exception e) {//####[106]####
            e.printStackTrace();//####[107]####
        }//####[108]####
    }//####[111]####
//####[114]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_boolean_method = null;//####[114]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet() {//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            try {//####[114]####
                __pt__intermediateMethod_NodeObject_ListString_boolean_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[114]####
                    NodeObject.class, List.class, boolean.class//####[114]####
                });//####[114]####
            } catch (Exception e) {//####[114]####
                e.printStackTrace();//####[114]####
            }//####[114]####
        }//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setTaskIdArgIndexes(0);//####[114]####
        taskinfo.addDependsOn(currentNode);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(0);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setTaskIdArgIndexes(1);//####[114]####
        taskinfo.addDependsOn(nodesToCheck);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[114]####
        taskinfo.addDependsOn(currentNode);//####[114]####
        taskinfo.addDependsOn(nodesToCheck);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(0);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(1);//####[114]####
        taskinfo.addDependsOn(nodesToCheck);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(1);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(1);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(0);//####[114]####
        taskinfo.addDependsOn(currentNode);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(0, 1);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setTaskIdArgIndexes(2);//####[114]####
        taskinfo.addDependsOn(isHeadThread);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[114]####
        taskinfo.addDependsOn(currentNode);//####[114]####
        taskinfo.addDependsOn(isHeadThread);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(0);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(2);//####[114]####
        taskinfo.addDependsOn(isHeadThread);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[114]####
        taskinfo.addDependsOn(nodesToCheck);//####[114]####
        taskinfo.addDependsOn(isHeadThread);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setTaskIdArgIndexes(0, 1, 2);//####[114]####
        taskinfo.addDependsOn(currentNode);//####[114]####
        taskinfo.addDependsOn(nodesToCheck);//####[114]####
        taskinfo.addDependsOn(isHeadThread);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(0);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[114]####
        taskinfo.addDependsOn(nodesToCheck);//####[114]####
        taskinfo.addDependsOn(isHeadThread);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(1);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(2);//####[114]####
        taskinfo.addDependsOn(isHeadThread);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(1);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[114]####
        taskinfo.addDependsOn(currentNode);//####[114]####
        taskinfo.addDependsOn(isHeadThread);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(0, 1);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(2);//####[114]####
        taskinfo.addDependsOn(isHeadThread);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(2);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(2);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(0);//####[114]####
        taskinfo.addDependsOn(currentNode);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(0, 2);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(2);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(1);//####[114]####
        taskinfo.addDependsOn(nodesToCheck);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(2);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[114]####
        taskinfo.addDependsOn(currentNode);//####[114]####
        taskinfo.addDependsOn(nodesToCheck);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(0, 2);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(1);//####[114]####
        taskinfo.addDependsOn(nodesToCheck);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(1, 2);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(1, 2);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setTaskIdArgIndexes(0);//####[114]####
        taskinfo.addDependsOn(currentNode);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread) {//####[114]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[114]####
        return intermediateMethod(currentNode, nodesToCheck, isHeadThread, new TaskInfo());//####[114]####
    }//####[114]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isHeadThread, TaskInfo taskinfo) {//####[114]####
        // ensure Method variable is set//####[114]####
        if (__pt__intermediateMethod_NodeObject_ListString_boolean_method == null) {//####[114]####
            __pt__intermediateMethod_NodeObject_ListString_boolean_ensureMethodVarSet();//####[114]####
        }//####[114]####
        taskinfo.setQueueArgIndexes(0, 1, 2);//####[114]####
        taskinfo.setIsPipeline(true);//####[114]####
        taskinfo.setParameters(currentNode, nodesToCheck, isHeadThread);//####[114]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_boolean_method);//####[114]####
        taskinfo.setInstance(this);//####[114]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[114]####
    }//####[114]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[114]####
        calculateTimeParallel(currentNode, nodesToCheck, isHeadThread);//####[115]####
    }//####[116]####
//####[116]####
//####[126]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[126]####
    public void calculateTimeParallel(NodeObject currentNode, List<String> nodesToCheck, boolean isHeadThread) {//####[126]####
        if (nodesToCheck.size() == 0) //####[134]####
        {//####[134]####
            if (maxTimeAtPoint(currentNode) < getMinimumTime()) //####[137]####
            {//####[137]####
                setMinimumTime(maxTimeAtPoint(currentNode));//####[140]####
                setBestSchedule(currentNode.getCurrentPath());//####[141]####
            }//####[142]####
            if (isHeadThread) //####[143]####
            {//####[143]####
                semaphore++;//####[144]####
            }//####[145]####
            return;//####[146]####
        }//####[147]####
        if (minimumTime != Integer.MAX_VALUE) //####[149]####
        {//####[149]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[151]####
            {//####[151]####
                if (isHeadThread) //####[152]####
                {//####[152]####
                    semaphore++;//####[153]####
                }//####[154]####
                return;//####[155]####
            }//####[156]####
        }//####[161]####
        for (String nodeToCheckStr : nodesToCheck) //####[165]####
        {//####[165]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[166]####
            {//####[166]####
                int count = 0;//####[167]####
                for (int i = 0; i < numberofProcessors; i++) //####[168]####
                {//####[168]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[169]####
                    {//####[169]####
                        count++;//####[170]####
                    }//####[171]####
                }//####[172]####
                int killtree = 0;//####[173]####
                if (count >= 2) //####[174]####
                {//####[174]####
                    killtree = count - 1;//####[175]####
                }//####[176]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[177]####
                {//####[177]####
                    ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[183]####
                    int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[184]####
                    String newNodeName = nodeToCheckStr;//####[187]####
                    int newProcessor = j;//####[188]####
                    int nodalWeight = getNodalWeight(newNodeName);//####[189]####
                    int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[190]####
                    int newEndTime = newStartTime + nodalWeight;//####[191]####
                    processorArray[newProcessor] = newEndTime;//####[192]####
                    NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[195]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[197]####
                    newUpdatedListWithoutCurrentNode.remove(newNodeName);//####[198]####
                    nodeNumber++;//####[199]####
                    if (semaphore > 0) //####[201]####
                    {//####[201]####
                        semaphore--;//####[202]####
                        TaskID newTask = intermediateMethod(nextNode, newUpdatedListWithoutCurrentNode, true);//####[203]####
                        taskGroup.add(newTask);//####[204]####
                    } else {//####[206]####
                        calculateTimeParallel(nextNode, newUpdatedListWithoutCurrentNode, false);//####[208]####
                    }//####[209]####
                }//####[210]####
            }//####[211]####
        }//####[212]####
    }//####[213]####
}//####[213]####
