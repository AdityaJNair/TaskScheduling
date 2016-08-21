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
    private static volatile Method __pt__calculateTime_NodeObject_ListString_boolean_method = null;//####[36]####
    private synchronized static void __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet() {//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            try {//####[36]####
                __pt__calculateTime_NodeObject_ListString_boolean_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__calculateTime", new Class[] {//####[36]####
                    NodeObject.class, List.class, boolean.class//####[36]####
                });//####[36]####
            } catch (Exception e) {//####[36]####
                e.printStackTrace();//####[36]####
            }//####[36]####
        }//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, List<String> nodesToCheck, boolean isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, List<String> nodesToCheck, boolean isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, List<String> nodesToCheck, boolean isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, List<String> nodesToCheck, boolean isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setTaskIdArgIndexes(0);//####[36]####
        taskinfo.addDependsOn(currentNode);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, boolean isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, boolean isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, TaskID<List<String>> nodesToCheck, boolean isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, TaskID<List<String>> nodesToCheck, boolean isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setTaskIdArgIndexes(1);//####[36]####
        taskinfo.addDependsOn(nodesToCheck);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[36]####
        taskinfo.addDependsOn(currentNode);//####[36]####
        taskinfo.addDependsOn(nodesToCheck);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, boolean isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(1);//####[36]####
        taskinfo.addDependsOn(nodesToCheck);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(1);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(1);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(0);//####[36]####
        taskinfo.addDependsOn(currentNode);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, boolean isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0, 1);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, List<String> nodesToCheck, TaskID<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, List<String> nodesToCheck, TaskID<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setTaskIdArgIndexes(2);//####[36]####
        taskinfo.addDependsOn(isThreaded);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[36]####
        taskinfo.addDependsOn(currentNode);//####[36]####
        taskinfo.addDependsOn(isThreaded);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskID<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(2);//####[36]####
        taskinfo.addDependsOn(isThreaded);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[36]####
        taskinfo.addDependsOn(nodesToCheck);//####[36]####
        taskinfo.addDependsOn(isThreaded);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setTaskIdArgIndexes(0, 1, 2);//####[36]####
        taskinfo.addDependsOn(currentNode);//####[36]####
        taskinfo.addDependsOn(nodesToCheck);//####[36]####
        taskinfo.addDependsOn(isThreaded);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskID<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(1, 2);//####[36]####
        taskinfo.addDependsOn(nodesToCheck);//####[36]####
        taskinfo.addDependsOn(isThreaded);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(1);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(2);//####[36]####
        taskinfo.addDependsOn(isThreaded);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(1);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(0, 2);//####[36]####
        taskinfo.addDependsOn(currentNode);//####[36]####
        taskinfo.addDependsOn(isThreaded);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskID<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0, 1);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(2);//####[36]####
        taskinfo.addDependsOn(isThreaded);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(2);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(2);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(0);//####[36]####
        taskinfo.addDependsOn(currentNode);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, BlockingQueue<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0, 2);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(2);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(1);//####[36]####
        taskinfo.addDependsOn(nodesToCheck);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(2);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[36]####
        taskinfo.addDependsOn(currentNode);//####[36]####
        taskinfo.addDependsOn(nodesToCheck);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0, 2);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(1);//####[36]####
        taskinfo.addDependsOn(nodesToCheck);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(1, 2);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(1, 2);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setTaskIdArgIndexes(0);//####[36]####
        taskinfo.addDependsOn(currentNode);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded) {//####[36]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[36]####
        return calculateTime(currentNode, nodesToCheck, isThreaded, new TaskInfo());//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public TaskID<Void> calculateTime(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, BlockingQueue<Boolean> isThreaded, TaskInfo taskinfo) {//####[36]####
        // ensure Method variable is set//####[36]####
        if (__pt__calculateTime_NodeObject_ListString_boolean_method == null) {//####[36]####
            __pt__calculateTime_NodeObject_ListString_boolean_ensureMethodVarSet();//####[36]####
        }//####[36]####
        taskinfo.setQueueArgIndexes(0, 1, 2);//####[36]####
        taskinfo.setIsPipeline(true);//####[36]####
        taskinfo.setParameters(currentNode, nodesToCheck, isThreaded);//####[36]####
        taskinfo.setMethod(__pt__calculateTime_NodeObject_ListString_boolean_method);//####[36]####
        taskinfo.setInstance(this);//####[36]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[36]####
    }//####[36]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[36]####
    public void __pt__calculateTime(NodeObject currentNode, List<String> nodesToCheck, boolean isThreaded) {//####[36]####
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
            TaskID<Void> newTask = calculateTimeParallel(nextNode, newUpdatedListWithoutCurrentNode);//####[70]####
            taskGroup.add(newTask);//####[71]####
        }//####[72]####
        try {//####[73]####
            taskGroup.waitTillFinished();//####[74]####
            System.out.println("Not waiting anymore");//####[75]####
        } catch (Exception e) {//####[76]####
            e.printStackTrace();//####[77]####
        }//####[78]####
    }//####[80]####
//####[80]####
//####[90]####
    private static volatile Method __pt__calculateTimeParallel_NodeObject_ListString_method = null;//####[90]####
    private synchronized static void __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet() {//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            try {//####[90]####
                __pt__calculateTimeParallel_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__calculateTimeParallel", new Class[] {//####[90]####
                    NodeObject.class, List.class//####[90]####
                });//####[90]####
            } catch (Exception e) {//####[90]####
                e.printStackTrace();//####[90]####
            }//####[90]####
        }//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(NodeObject currentNode, List<String> nodesToCheck) {//####[90]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[90]####
        return calculateTimeParallel(currentNode, nodesToCheck, new TaskInfo());//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[90]####
        // ensure Method variable is set//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet();//####[90]####
        }//####[90]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[90]####
        taskinfo.setMethod(__pt__calculateTimeParallel_NodeObject_ListString_method);//####[90]####
        taskinfo.setInstance(this);//####[90]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[90]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[90]####
        return calculateTimeParallel(currentNode, nodesToCheck, new TaskInfo());//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[90]####
        // ensure Method variable is set//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet();//####[90]####
        }//####[90]####
        taskinfo.setTaskIdArgIndexes(0);//####[90]####
        taskinfo.addDependsOn(currentNode);//####[90]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[90]####
        taskinfo.setMethod(__pt__calculateTimeParallel_NodeObject_ListString_method);//####[90]####
        taskinfo.setInstance(this);//####[90]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[90]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[90]####
        return calculateTimeParallel(currentNode, nodesToCheck, new TaskInfo());//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[90]####
        // ensure Method variable is set//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet();//####[90]####
        }//####[90]####
        taskinfo.setQueueArgIndexes(0);//####[90]####
        taskinfo.setIsPipeline(true);//####[90]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[90]####
        taskinfo.setMethod(__pt__calculateTimeParallel_NodeObject_ListString_method);//####[90]####
        taskinfo.setInstance(this);//####[90]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[90]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[90]####
        return calculateTimeParallel(currentNode, nodesToCheck, new TaskInfo());//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[90]####
        // ensure Method variable is set//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet();//####[90]####
        }//####[90]####
        taskinfo.setTaskIdArgIndexes(1);//####[90]####
        taskinfo.addDependsOn(nodesToCheck);//####[90]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[90]####
        taskinfo.setMethod(__pt__calculateTimeParallel_NodeObject_ListString_method);//####[90]####
        taskinfo.setInstance(this);//####[90]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[90]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[90]####
        return calculateTimeParallel(currentNode, nodesToCheck, new TaskInfo());//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[90]####
        // ensure Method variable is set//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet();//####[90]####
        }//####[90]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[90]####
        taskinfo.addDependsOn(currentNode);//####[90]####
        taskinfo.addDependsOn(nodesToCheck);//####[90]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[90]####
        taskinfo.setMethod(__pt__calculateTimeParallel_NodeObject_ListString_method);//####[90]####
        taskinfo.setInstance(this);//####[90]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[90]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[90]####
        return calculateTimeParallel(currentNode, nodesToCheck, new TaskInfo());//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[90]####
        // ensure Method variable is set//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet();//####[90]####
        }//####[90]####
        taskinfo.setQueueArgIndexes(0);//####[90]####
        taskinfo.setIsPipeline(true);//####[90]####
        taskinfo.setTaskIdArgIndexes(1);//####[90]####
        taskinfo.addDependsOn(nodesToCheck);//####[90]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[90]####
        taskinfo.setMethod(__pt__calculateTimeParallel_NodeObject_ListString_method);//####[90]####
        taskinfo.setInstance(this);//####[90]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[90]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[90]####
        return calculateTimeParallel(currentNode, nodesToCheck, new TaskInfo());//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[90]####
        // ensure Method variable is set//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet();//####[90]####
        }//####[90]####
        taskinfo.setQueueArgIndexes(1);//####[90]####
        taskinfo.setIsPipeline(true);//####[90]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[90]####
        taskinfo.setMethod(__pt__calculateTimeParallel_NodeObject_ListString_method);//####[90]####
        taskinfo.setInstance(this);//####[90]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[90]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[90]####
        return calculateTimeParallel(currentNode, nodesToCheck, new TaskInfo());//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[90]####
        // ensure Method variable is set//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet();//####[90]####
        }//####[90]####
        taskinfo.setQueueArgIndexes(1);//####[90]####
        taskinfo.setIsPipeline(true);//####[90]####
        taskinfo.setTaskIdArgIndexes(0);//####[90]####
        taskinfo.addDependsOn(currentNode);//####[90]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[90]####
        taskinfo.setMethod(__pt__calculateTimeParallel_NodeObject_ListString_method);//####[90]####
        taskinfo.setInstance(this);//####[90]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[90]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[90]####
        return calculateTimeParallel(currentNode, nodesToCheck, new TaskInfo());//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public TaskID<Void> calculateTimeParallel(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[90]####
        // ensure Method variable is set//####[90]####
        if (__pt__calculateTimeParallel_NodeObject_ListString_method == null) {//####[90]####
            __pt__calculateTimeParallel_NodeObject_ListString_ensureMethodVarSet();//####[90]####
        }//####[90]####
        taskinfo.setQueueArgIndexes(0, 1);//####[90]####
        taskinfo.setIsPipeline(true);//####[90]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[90]####
        taskinfo.setMethod(__pt__calculateTimeParallel_NodeObject_ListString_method);//####[90]####
        taskinfo.setInstance(this);//####[90]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[90]####
    }//####[90]####
    /** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * TODO: Change minimumtime and bestSchedule to thread safe options. Make superclass ones private
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 *///####[90]####
    public void __pt__calculateTimeParallel(NodeObject currentNode, List<String> nodesToCheck) {//####[90]####
        if (nodesToCheck.size() == 0) //####[93]####
        {//####[93]####
            if (maxTimeAtPoint(currentNode) < getMinimumTime()) //####[96]####
            {//####[96]####
                setMinimumTime(maxTimeAtPoint(currentNode));//####[99]####
                setBestSchedule(currentNode.getCurrentPath());//####[100]####
            }//####[101]####
            return;//####[102]####
        }//####[103]####
        if (minimumTime != Integer.MAX_VALUE) //####[105]####
        {//####[105]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[107]####
            {//####[107]####
                return;//####[108]####
            }//####[109]####
        }//####[114]####
        for (String nodeToCheckStr : nodesToCheck) //####[118]####
        {//####[118]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[119]####
            {//####[119]####
                int count = 0;//####[120]####
                for (int i = 0; i < numberofProcessors; i++) //####[121]####
                {//####[121]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[122]####
                    {//####[122]####
                        count++;//####[123]####
                    }//####[124]####
                }//####[125]####
                int killtree = 0;//####[126]####
                if (count >= 2) //####[127]####
                {//####[127]####
                    killtree = count - 1;//####[128]####
                }//####[129]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[130]####
                {//####[130]####
                    ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[136]####
                    int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[137]####
                    String newNodeName = nodeToCheckStr;//####[140]####
                    int newProcessor = j;//####[141]####
                    int nodalWeight = getNodalWeight(newNodeName);//####[142]####
                    int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[143]####
                    int newEndTime = newStartTime + nodalWeight;//####[144]####
                    processorArray[newProcessor] = newEndTime;//####[145]####
                    NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[148]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[150]####
                    newUpdatedListWithoutCurrentNode.remove(newNodeName);//####[151]####
                    nodeNumber++;//####[152]####
                    calculateTimeParallel(nextNode, newUpdatedListWithoutCurrentNode);//####[155]####
                }//####[156]####
            }//####[157]####
        }//####[158]####
    }//####[159]####
//####[159]####
}//####[159]####
