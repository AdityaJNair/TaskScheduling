package visual;//####[1]####
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
import org.graphstream.graph.Edge;//####[12]####
import org.graphstream.graph.Graph;//####[13]####
import org.graphstream.graph.Node;//####[14]####
import org.graphstream.graph.implementations.SingleGraph;//####[15]####
import javax.swing.*;//####[16]####
//####[16]####
//-- ParaTask related imports//####[16]####
import pt.runtime.*;//####[16]####
import java.util.concurrent.ExecutionException;//####[16]####
import java.util.concurrent.locks.*;//####[16]####
import java.lang.reflect.*;//####[16]####
import pt.runtime.GuiThread;//####[16]####
import java.util.concurrent.BlockingQueue;//####[16]####
import java.util.ArrayList;//####[16]####
import java.util.List;//####[16]####
//####[16]####
public class ParallelSearchVisualTree extends SolutionTreeVisual {//####[18]####
    static{ParaTask.init();}//####[18]####
    /*  ParaTask helper method to access private/protected slots *///####[18]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[18]####
        if (m.getParameterTypes().length == 0)//####[18]####
            m.invoke(instance);//####[18]####
        else if ((m.getParameterTypes().length == 1))//####[18]####
            m.invoke(instance, arg);//####[18]####
        else //####[18]####
            m.invoke(instance, arg, interResult);//####[18]####
    }//####[18]####
//####[20]####
    int semaphore = UserOptions.getInstance().getParallelThreads();//####[20]####
//####[21]####
    FifoLifoQueue<List<NodeObject>> bestScheduleThreaded = new FifoLifoQueue<List<NodeObject>>();//####[21]####
//####[22]####
    FifoLifoQueue<Integer> bestTimeThreaded = new FifoLifoQueue<Integer>();//####[22]####
//####[23]####
    List<String> sourceNodes = inputGraph.getSourceNodes();//####[23]####
//####[24]####
    TaskIDGroup taskGroup = new TaskIDGroup(UserOptions.getInstance().getParallelThreads() + 5);//####[24]####
//####[28]####
    /**
	 * @param inputGraph
	 *///####[28]####
    public ParallelSearchVisualTree(AdjacencyList inputGraph) {//####[28]####
        super(inputGraph);//####[29]####
        bestTimeThreaded.addGlobal(Integer.MAX_VALUE);//####[30]####
    }//####[32]####
//####[34]####
    public void recursiveMethod(NodeObject currentNode, List<String> nodesToCheck, boolean isSubtask) {//####[34]####
        if (nodesToCheck.size() == 0) //####[37]####
        {//####[37]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[41]####
            {//####[41]####
                setState2(currentNode);//####[42]####
                minimumTime = maxTimeAtPoint(currentNode);//####[43]####
                bestSchedule = currentNode.getCurrentPath();//####[44]####
                return;//####[45]####
            }//####[46]####
        }//####[47]####
        if (minimumTime != Integer.MAX_VALUE) //####[49]####
        {//####[49]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[51]####
            {//####[51]####
                return;//####[52]####
            }//####[53]####
            if (calculateLowerBound(currentNode, nodesToCheck) >= minimumTime) //####[55]####
            {//####[55]####
                return;//####[56]####
            }//####[57]####
        }//####[58]####
        TaskID newTask = null;//####[60]####
        List<String> legalNodes = new ArrayList<String>();//####[61]####
        for (String nodeToCheckStr : nodesToCheck) //####[64]####
        {//####[64]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[65]####
            {//####[65]####
                legalNodes.add(nodeToCheckStr);//####[66]####
            }//####[67]####
        }//####[68]####
        int count = 0;//####[69]####
        if (legalNodes.size() == 1) //####[70]####
        {//####[70]####
            for (int i = 0; i < numberofProcessors; i++) //####[72]####
            {//####[72]####
                NodeObject newNode = createNextNode(currentNode, legalNodes.get(0), i);//####[73]####
                List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[75]####
                newUpdatedListWithoutCurrentNode.remove(legalNodes.get(0));//####[76]####
                nodeNumber++;//####[77]####
                recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, isSubtask);//####[79]####
            }//####[80]####
        } else {//####[82]####
            for (String nodeToCheckStr : legalNodes) //####[84]####
            {//####[84]####
                for (int i = 0; i < numberofProcessors; i++) //####[86]####
                {//####[86]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[87]####
                    {//####[87]####
                        count++;//####[88]####
                    }//####[89]####
                }//####[90]####
                int killtree = 0;//####[91]####
                if (count >= 2) //####[92]####
                {//####[92]####
                    killtree = count - 1;//####[93]####
                }//####[94]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[95]####
                {//####[95]####
                    NodeObject newNode = createNextNode(currentNode, nodeToCheckStr, j);//####[96]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[99]####
                    newUpdatedListWithoutCurrentNode.remove(nodeToCheckStr);//####[100]####
                    nodeNumber++;//####[101]####
                    if (isSubtask) //####[103]####
                    {//####[103]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, true);//####[104]####
                        continue;//####[105]####
                    }//####[106]####
                    if (semaphore == 0) //####[108]####
                    {//####[108]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, false);//####[110]####
                    } else {//####[111]####
                        semaphore--;//####[113]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[114]####
                        taskGroup.add(newTask);//####[116]####
                    }//####[117]####
                }//####[118]####
            }//####[119]####
        }//####[120]####
        if (isSubtask) //####[121]####
        {//####[121]####
            return;//####[122]####
        }//####[123]####
        try {//####[124]####
            taskGroup.waitTillFinished();//####[125]####
        } catch (Exception e) {//####[126]####
            System.out.println("Error in wait");//####[127]####
            e.printStackTrace();//####[128]####
        }//####[129]####
    }//####[130]####
//####[132]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[132]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            try {//####[132]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[132]####
                    NodeObject.class, List.class//####[132]####
                });//####[132]####
            } catch (Exception e) {//####[132]####
                e.printStackTrace();//####[132]####
            }//####[132]####
        }//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setTaskIdArgIndexes(0);//####[132]####
        taskinfo.addDependsOn(currentNode);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(0);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setTaskIdArgIndexes(1);//####[132]####
        taskinfo.addDependsOn(nodesToCheck);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[132]####
        taskinfo.addDependsOn(currentNode);//####[132]####
        taskinfo.addDependsOn(nodesToCheck);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(0);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setTaskIdArgIndexes(1);//####[132]####
        taskinfo.addDependsOn(nodesToCheck);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(1);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(1);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setTaskIdArgIndexes(0);//####[132]####
        taskinfo.addDependsOn(currentNode);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[132]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[132]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[132]####
    }//####[132]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[132]####
        // ensure Method variable is set//####[132]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[132]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[132]####
        }//####[132]####
        taskinfo.setQueueArgIndexes(0, 1);//####[132]####
        taskinfo.setIsPipeline(true);//####[132]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[132]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[132]####
        taskinfo.setInstance(this);//####[132]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[132]####
    }//####[132]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[132]####
        recursiveMethod(currentNode, nodesToCheck, true);//####[133]####
        semaphore++;//####[134]####
    }//####[136]####
//####[136]####
//####[138]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[138]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[141]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[142]####
        String newNodeName = nodeToCheckStr;//####[145]####
        int newProcessor = processorNumber;//####[146]####
        int nodalWeight = getNodalWeight(newNodeName);//####[147]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[148]####
        int newEndTime = newStartTime + nodalWeight;//####[149]####
        processorArray[newProcessor] = newEndTime;//####[150]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[153]####
        return nextNode;//####[154]####
    }//####[155]####
//####[157]####
    private void setState2(NodeObject currentNode) {//####[157]####
        bestTimeCount++;//####[159]####
        for (Edge e : bestTimeTree.getEdgeSet()) //####[162]####
        {//####[162]####
            e.addAttribute("ui.style", "fill-color: rgb(0,0,0);");//####[163]####
        }//####[164]####
        bestTimeLabel.setText("Current Best Time: " + maxTimeAtPoint(currentNode));//####[165]####
        bestTimeCountLabel.setText("Faster Schedules Found: " + Long.toString(bestTimeCount));//####[166]####
        String oldNode = new String();//####[167]####
        String newNode = new String();//####[168]####
        int i = 0;//####[169]####
        Edge e;//####[170]####
        String bestPath = new String();//####[171]####
        for (NodeObject node : currentNode.getCurrentPath()) //####[172]####
        {//####[172]####
            if (node.getNodeName().equals("rootNode")) //####[173]####
            continue;//####[174]####
            newNode += node.getNodeName() + node.getProcessor();//####[175]####
            if (bestTimeTree.getNode(newNode) == null) //####[176]####
            {//####[176]####
                Node n = bestTimeTree.addNode(newNode);//####[177]####
                n.addAttribute("ui.label", node.getNodeName() + "(" + node.getProcessor() + ")");//####[179]####
                n.addAttribute("layout.frozen");//####[181]####
                n.addAttribute("y", -i * 15);//####[183]####
                n.addAttribute("x", nid);//####[185]####
            }//####[186]####
            if (i > 0) //####[187]####
            {//####[187]####
                bestTimeTree.removeEdge(newNode, oldNode);//####[188]####
                e = bestTimeTree.addEdge(Integer.toString(nid), newNode, oldNode);//####[189]####
                e.setAttribute("ui.style", "fill-color:red;");//####[190]####
            }//####[192]####
            oldNode = newNode;//####[194]####
            i++;//####[195]####
            nid++;//####[196]####
            try {//####[198]####
                Thread.sleep(50);//####[199]####
            } catch (Exception e2) {//####[200]####
            }//####[202]####
            bestPath += node.getNodeName() + "(" + (node.getProcessor() + 1) + ") ";//####[203]####
        }//####[204]####
        bestTimeScheduleLabel.setText("Best Path: " + bestPath);//####[205]####
        if (maxTimeAtPoint(currentNode) == minimumTime) //####[206]####
        {//####[206]####
            equalBestTimeCount++;//####[207]####
            equalBestTimeCountLabel.setText("Duplicate Schedules at Current Optimal Time Found: " + Long.toString(equalBestTimeCount));//####[208]####
        }//####[209]####
    }//####[210]####
}//####[210]####
