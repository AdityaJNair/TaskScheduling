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
    FifoLifoQueue<List<NodeObject>> bestScheduleThreaded = new FifoLifoQueue<List<NodeObject>>();//####[12]####
//####[13]####
    FifoLifoQueue<Integer> bestTimeThreaded = new FifoLifoQueue<Integer>();//####[13]####
//####[18]####
    /**
	 * @param inputGraph
	 *///####[18]####
    public Parallalism(AdjacencyList inputGraph) {//####[18]####
        super(inputGraph);//####[19]####
        bestTimeThreaded.addGlobal(Integer.MAX_VALUE);//####[20]####
    }//####[22]####
//####[24]####
    private static volatile Method __pt__parallel_NodeObject_ListString_method = null;//####[24]####
    private synchronized static void __pt__parallel_NodeObject_ListString_ensureMethodVarSet() {//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            try {//####[24]####
                __pt__parallel_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__parallel", new Class[] {//####[24]####
                    NodeObject.class, List.class//####[24]####
                });//####[24]####
            } catch (Exception e) {//####[24]####
                e.printStackTrace();//####[24]####
            }//####[24]####
        }//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(NodeObject currentNode, List<String> nodesToCheck) {//####[24]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[24]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[24]####
        // ensure Method variable is set//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[24]####
        }//####[24]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[24]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[24]####
        taskinfo.setInstance(this);//####[24]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[24]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[24]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[24]####
        // ensure Method variable is set//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[24]####
        }//####[24]####
        taskinfo.setTaskIdArgIndexes(0);//####[24]####
        taskinfo.addDependsOn(currentNode);//####[24]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[24]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[24]####
        taskinfo.setInstance(this);//####[24]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[24]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[24]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[24]####
        // ensure Method variable is set//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[24]####
        }//####[24]####
        taskinfo.setQueueArgIndexes(0);//####[24]####
        taskinfo.setIsPipeline(true);//####[24]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[24]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[24]####
        taskinfo.setInstance(this);//####[24]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[24]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[24]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[24]####
        // ensure Method variable is set//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[24]####
        }//####[24]####
        taskinfo.setTaskIdArgIndexes(1);//####[24]####
        taskinfo.addDependsOn(nodesToCheck);//####[24]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[24]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[24]####
        taskinfo.setInstance(this);//####[24]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[24]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[24]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[24]####
        // ensure Method variable is set//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[24]####
        }//####[24]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[24]####
        taskinfo.addDependsOn(currentNode);//####[24]####
        taskinfo.addDependsOn(nodesToCheck);//####[24]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[24]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[24]####
        taskinfo.setInstance(this);//####[24]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[24]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[24]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[24]####
        // ensure Method variable is set//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[24]####
        }//####[24]####
        taskinfo.setQueueArgIndexes(0);//####[24]####
        taskinfo.setIsPipeline(true);//####[24]####
        taskinfo.setTaskIdArgIndexes(1);//####[24]####
        taskinfo.addDependsOn(nodesToCheck);//####[24]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[24]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[24]####
        taskinfo.setInstance(this);//####[24]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[24]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[24]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[24]####
        // ensure Method variable is set//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[24]####
        }//####[24]####
        taskinfo.setQueueArgIndexes(1);//####[24]####
        taskinfo.setIsPipeline(true);//####[24]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[24]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[24]####
        taskinfo.setInstance(this);//####[24]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[24]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[24]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[24]####
        // ensure Method variable is set//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[24]####
        }//####[24]####
        taskinfo.setQueueArgIndexes(1);//####[24]####
        taskinfo.setIsPipeline(true);//####[24]####
        taskinfo.setTaskIdArgIndexes(0);//####[24]####
        taskinfo.addDependsOn(currentNode);//####[24]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[24]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[24]####
        taskinfo.setInstance(this);//####[24]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[24]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[24]####
        return parallel(currentNode, nodesToCheck, new TaskInfo());//####[24]####
    }//####[24]####
    public TaskID<Void> parallel(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[24]####
        // ensure Method variable is set//####[24]####
        if (__pt__parallel_NodeObject_ListString_method == null) {//####[24]####
            __pt__parallel_NodeObject_ListString_ensureMethodVarSet();//####[24]####
        }//####[24]####
        taskinfo.setQueueArgIndexes(0, 1);//####[24]####
        taskinfo.setIsPipeline(true);//####[24]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[24]####
        taskinfo.setMethod(__pt__parallel_NodeObject_ListString_method);//####[24]####
        taskinfo.setInstance(this);//####[24]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[24]####
    }//####[24]####
    public void __pt__parallel(NodeObject currentNode, List<String> nodesToCheck) {//####[24]####
        calculateTime(currentNode, nodesToCheck);//####[25]####
        return;//####[26]####
    }//####[27]####
//####[27]####
//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public void calculateTime(NodeObject currentNode, List<String> nodesToCheck, boolean isThreaded) {//####[36]####
        if (!isThreaded) //####[37]####
        {//####[37]####
            calculateTime(currentNode, nodesToCheck);//####[38]####
            return;//####[39]####
        }//####[40]####
        List<String> sources = inputGraph.getSourceNodes();//####[41]####
        int numberOfSources = sources.size();//####[42]####
        TaskIDGroup taskGroup = new TaskIDGroup(numberOfSources);//####[44]####
        for (int i = 0; i < numberOfSources; i++) //####[46]####
        {//####[46]####
            String sourceName = sources.get(i);//####[47]####
            ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[51]####
            int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[52]####
            String newNodeName = sourceName;//####[55]####
            int newProcessor = 0;//####[56]####
            int nodalWeight = getNodalWeight(newNodeName);//####[57]####
            int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[58]####
            int newEndTime = newStartTime + nodalWeight;//####[59]####
            processorArray[newProcessor] = newEndTime;//####[60]####
            NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[63]####
            List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[65]####
            newUpdatedListWithoutCurrentNode.remove(newNodeName);//####[66]####
            nodeNumber++;//####[67]####
            System.out.println("Creating thread number " + (i + 1));//####[69]####
            TaskID<Void> newTask = intermediateMethod(nextNode, newUpdatedListWithoutCurrentNode);//####[70]####
            taskGroup.add(newTask);//####[71]####
        }//####[72]####
        try {//####[73]####
            taskGroup.waitTillFinished();//####[74]####
            System.out.println("Not waiting anymore");//####[75]####
        } catch (Exception e) {//####[76]####
            e.printStackTrace();//####[77]####
        }//####[78]####
    }//####[80]####
//####[82]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[82]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            try {//####[82]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[82]####
                    NodeObject.class, List.class//####[82]####
                });//####[82]####
            } catch (Exception e) {//####[82]####
                e.printStackTrace();//####[82]####
            }//####[82]####
        }//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[82]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[82]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[82]####
        // ensure Method variable is set//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[82]####
        }//####[82]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[82]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[82]####
        taskinfo.setInstance(this);//####[82]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[82]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[82]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[82]####
        // ensure Method variable is set//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[82]####
        }//####[82]####
        taskinfo.setTaskIdArgIndexes(0);//####[82]####
        taskinfo.addDependsOn(currentNode);//####[82]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[82]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[82]####
        taskinfo.setInstance(this);//####[82]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[82]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[82]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[82]####
        // ensure Method variable is set//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[82]####
        }//####[82]####
        taskinfo.setQueueArgIndexes(0);//####[82]####
        taskinfo.setIsPipeline(true);//####[82]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[82]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[82]####
        taskinfo.setInstance(this);//####[82]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[82]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[82]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[82]####
        // ensure Method variable is set//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[82]####
        }//####[82]####
        taskinfo.setTaskIdArgIndexes(1);//####[82]####
        taskinfo.addDependsOn(nodesToCheck);//####[82]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[82]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[82]####
        taskinfo.setInstance(this);//####[82]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[82]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[82]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[82]####
        // ensure Method variable is set//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[82]####
        }//####[82]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[82]####
        taskinfo.addDependsOn(currentNode);//####[82]####
        taskinfo.addDependsOn(nodesToCheck);//####[82]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[82]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[82]####
        taskinfo.setInstance(this);//####[82]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[82]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[82]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[82]####
        // ensure Method variable is set//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[82]####
        }//####[82]####
        taskinfo.setQueueArgIndexes(0);//####[82]####
        taskinfo.setIsPipeline(true);//####[82]####
        taskinfo.setTaskIdArgIndexes(1);//####[82]####
        taskinfo.addDependsOn(nodesToCheck);//####[82]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[82]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[82]####
        taskinfo.setInstance(this);//####[82]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[82]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[82]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[82]####
        // ensure Method variable is set//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[82]####
        }//####[82]####
        taskinfo.setQueueArgIndexes(1);//####[82]####
        taskinfo.setIsPipeline(true);//####[82]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[82]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[82]####
        taskinfo.setInstance(this);//####[82]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[82]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[82]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[82]####
        // ensure Method variable is set//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[82]####
        }//####[82]####
        taskinfo.setQueueArgIndexes(1);//####[82]####
        taskinfo.setIsPipeline(true);//####[82]####
        taskinfo.setTaskIdArgIndexes(0);//####[82]####
        taskinfo.addDependsOn(currentNode);//####[82]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[82]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[82]####
        taskinfo.setInstance(this);//####[82]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[82]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[82]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[82]####
    }//####[82]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[82]####
        // ensure Method variable is set//####[82]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[82]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[82]####
        }//####[82]####
        taskinfo.setQueueArgIndexes(0, 1);//####[82]####
        taskinfo.setIsPipeline(true);//####[82]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[82]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[82]####
        taskinfo.setInstance(this);//####[82]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[82]####
    }//####[82]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[82]####
        calculateTimeParallel(currentNode, nodesToCheck);//####[83]####
    }//####[84]####
//####[84]####
//####[94]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[94]####
    public void calculateTimeParallel(NodeObject currentNode, List<String> nodesToCheck) {//####[94]####
        if (CurrentTask.insideTask()) //####[95]####
        {//####[95]####
        } else {//####[97]####
            System.out.println("Executed as a method sequentialsefsg web");//####[98]####
        }//####[100]####
        if (nodesToCheck.size() == 0) //####[102]####
        {//####[102]####
            if (maxTimeAtPoint(currentNode) < getMinimumTime()) //####[105]####
            {//####[105]####
                setMinimumTime(maxTimeAtPoint(currentNode));//####[108]####
                setBestSchedule(currentNode.getCurrentPath());//####[109]####
            }//####[110]####
            return;//####[111]####
        }//####[112]####
        if (minimumTime != Integer.MAX_VALUE) //####[114]####
        {//####[114]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[116]####
            {//####[116]####
                return;//####[117]####
            }//####[118]####
        }//####[123]####
        for (String nodeToCheckStr : nodesToCheck) //####[127]####
        {//####[127]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[128]####
            {//####[128]####
                int count = 0;//####[129]####
                for (int i = 0; i < numberofProcessors; i++) //####[130]####
                {//####[130]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[131]####
                    {//####[131]####
                        count++;//####[132]####
                    }//####[133]####
                }//####[134]####
                int killtree = 0;//####[135]####
                if (count >= 2) //####[136]####
                {//####[136]####
                    killtree = count - 1;//####[137]####
                }//####[138]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[139]####
                {//####[139]####
                    ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[145]####
                    int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[146]####
                    String newNodeName = nodeToCheckStr;//####[149]####
                    int newProcessor = j;//####[150]####
                    int nodalWeight = getNodalWeight(newNodeName);//####[151]####
                    int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[152]####
                    int newEndTime = newStartTime + nodalWeight;//####[153]####
                    processorArray[newProcessor] = newEndTime;//####[154]####
                    NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[157]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[159]####
                    newUpdatedListWithoutCurrentNode.remove(newNodeName);//####[160]####
                    nodeNumber++;//####[161]####
                    calculateTimeParallel(nextNode, newUpdatedListWithoutCurrentNode);//####[164]####
                }//####[165]####
            }//####[166]####
        }//####[167]####
    }//####[168]####
}//####[168]####
