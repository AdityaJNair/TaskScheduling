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
            validScheduleCount++;//####[38]####
            validScheduleCountLabel.setText("Valid Schedules Discovered: " + Long.toString(validScheduleCount));//####[39]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[42]####
            {//####[42]####
                bestTimeCount++;//####[43]####
                for (Edge e : bestTimeTree.getEdgeSet()) //####[46]####
                {//####[46]####
                    e.addAttribute("ui.style", "fill-color: rgb(0,0,0);");//####[47]####
                }//####[48]####
                minimumTime = maxTimeAtPoint(currentNode);//####[49]####
                bestTimeLabel.setText("Current Best Time: " + maxTimeAtPoint(currentNode));//####[50]####
                bestTimeCountLabel.setText("Faster Schedules Found: " + Long.toString(bestTimeCount));//####[51]####
                bestSchedule = currentNode.getCurrentPath();//####[52]####
                String oldNode = new String();//####[53]####
                String newNode = new String();//####[54]####
                int i = 0;//####[55]####
                Edge e;//####[56]####
                String bestPath = new String();//####[57]####
                for (NodeObject node : currentNode.getCurrentPath()) //####[58]####
                {//####[58]####
                    if (node.getNodeName().equals("rootNode")) //####[59]####
                    continue;//####[60]####
                    newNode += node.getNodeName() + node.getProcessor();//####[61]####
                    if (bestTimeTree.getNode(newNode) == null) //####[62]####
                    {//####[62]####
                        Node n = bestTimeTree.addNode(newNode);//####[63]####
                        n.addAttribute("ui.label", newNode);//####[66]####
                        n.addAttribute("layout.frozen");//####[67]####
                        n.addAttribute("y", -i * 15);//####[69]####
                        n.addAttribute("x", nid);//####[71]####
                    }//####[72]####
                    if (i > 0) //####[73]####
                    {//####[73]####
                        bestTimeTree.removeEdge(newNode, oldNode);//####[74]####
                        e = bestTimeTree.addEdge(Integer.toString(nid), newNode, oldNode);//####[75]####
                        e.setAttribute("ui.style", "fill-color:red;");//####[76]####
                    }//####[78]####
                    oldNode = newNode;//####[80]####
                    i++;//####[81]####
                    nid++;//####[82]####
                    try {//####[84]####
                        Thread.sleep(50);//####[85]####
                    } catch (Exception e2) {//####[86]####
                    }//####[88]####
                    bestPath += node.getNodeName() + "(" + (node.getProcessor() + 1) + ") ";//####[89]####
                }//####[90]####
                bestTimeScheduleLabel.setText("Best Path: " + bestPath);//####[91]####
            } else if (maxTimeAtPoint(currentNode) == minimumTime) //####[92]####
            {//####[92]####
                equalBestTimeCount++;//####[93]####
                equalBestTimeCountLabel.setText("Duplicate Schedules at Current Optimal Time Found: " + Long.toString(equalBestTimeCount));//####[94]####
            }//####[95]####
            return;//####[96]####
        }//####[97]####
        if (minimumTime != Integer.MAX_VALUE) //####[100]####
        {//####[100]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[102]####
            {//####[102]####
                return;//####[103]####
            }//####[104]####
            if (calculateLowerBound(currentNode, nodesToCheck) >= minimumTime) //####[106]####
            {//####[106]####
                return;//####[107]####
            }//####[108]####
        }//####[109]####
        TaskID newTask = null;//####[111]####
        List<String> legalNodes = new ArrayList<String>();//####[112]####
        for (String nodeToCheckStr : nodesToCheck) //####[115]####
        {//####[115]####
            if (isValidOption(nodeToCheckStr, nodesToCheck)) //####[116]####
            {//####[116]####
                legalNodes.add(nodeToCheckStr);//####[117]####
            }//####[118]####
        }//####[119]####
        int count = 0;//####[120]####
        if (legalNodes.size() == 1) //####[121]####
        {//####[121]####
            for (int i = 0; i < numberofProcessors; i++) //####[123]####
            {//####[123]####
                NodeObject newNode = createNextNode(currentNode, legalNodes.get(0), i);//####[124]####
                List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[126]####
                newUpdatedListWithoutCurrentNode.remove(legalNodes.get(0));//####[127]####
                nodeNumber++;//####[128]####
                recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, isSubtask);//####[130]####
            }//####[131]####
        } else {//####[133]####
            for (String nodeToCheckStr : legalNodes) //####[135]####
            {//####[135]####
                for (int i = 0; i < numberofProcessors; i++) //####[137]####
                {//####[137]####
                    if (currentNode.getTimeWeightOnEachProcessor()[i] == 0) //####[138]####
                    {//####[138]####
                        count++;//####[139]####
                    }//####[140]####
                }//####[141]####
                int killtree = 0;//####[142]####
                if (count >= 2) //####[143]####
                {//####[143]####
                    killtree = count - 1;//####[144]####
                }//####[145]####
                for (int j = 0; j < (numberofProcessors - killtree); j++) //####[146]####
                {//####[146]####
                    NodeObject newNode = createNextNode(currentNode, nodeToCheckStr, j);//####[147]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[150]####
                    newUpdatedListWithoutCurrentNode.remove(nodeToCheckStr);//####[151]####
                    nodeNumber++;//####[152]####
                    if (isSubtask) //####[154]####
                    {//####[154]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, true);//####[155]####
                        continue;//####[156]####
                    }//####[157]####
                    if (semaphore == 0) //####[159]####
                    {//####[159]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, false);//####[161]####
                    } else {//####[162]####
                        semaphore--;//####[164]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[165]####
                        taskGroup.add(newTask);//####[167]####
                    }//####[168]####
                }//####[169]####
            }//####[170]####
        }//####[171]####
        if (isSubtask) //####[172]####
        {//####[172]####
            return;//####[173]####
        }//####[174]####
        try {//####[175]####
        } catch (Exception e) {//####[177]####
            e.printStackTrace();//####[178]####
        }//####[179]####
    }//####[180]####
//####[182]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[182]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            try {//####[182]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[182]####
                    NodeObject.class, List.class//####[182]####
                });//####[182]####
            } catch (Exception e) {//####[182]####
                e.printStackTrace();//####[182]####
            }//####[182]####
        }//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[182]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[182]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[182]####
        // ensure Method variable is set//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[182]####
        }//####[182]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[182]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[182]####
        taskinfo.setInstance(this);//####[182]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[182]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[182]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[182]####
        // ensure Method variable is set//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[182]####
        }//####[182]####
        taskinfo.setTaskIdArgIndexes(0);//####[182]####
        taskinfo.addDependsOn(currentNode);//####[182]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[182]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[182]####
        taskinfo.setInstance(this);//####[182]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[182]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[182]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[182]####
        // ensure Method variable is set//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[182]####
        }//####[182]####
        taskinfo.setQueueArgIndexes(0);//####[182]####
        taskinfo.setIsPipeline(true);//####[182]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[182]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[182]####
        taskinfo.setInstance(this);//####[182]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[182]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[182]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[182]####
        // ensure Method variable is set//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[182]####
        }//####[182]####
        taskinfo.setTaskIdArgIndexes(1);//####[182]####
        taskinfo.addDependsOn(nodesToCheck);//####[182]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[182]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[182]####
        taskinfo.setInstance(this);//####[182]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[182]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[182]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[182]####
        // ensure Method variable is set//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[182]####
        }//####[182]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[182]####
        taskinfo.addDependsOn(currentNode);//####[182]####
        taskinfo.addDependsOn(nodesToCheck);//####[182]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[182]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[182]####
        taskinfo.setInstance(this);//####[182]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[182]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[182]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[182]####
        // ensure Method variable is set//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[182]####
        }//####[182]####
        taskinfo.setQueueArgIndexes(0);//####[182]####
        taskinfo.setIsPipeline(true);//####[182]####
        taskinfo.setTaskIdArgIndexes(1);//####[182]####
        taskinfo.addDependsOn(nodesToCheck);//####[182]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[182]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[182]####
        taskinfo.setInstance(this);//####[182]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[182]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[182]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[182]####
        // ensure Method variable is set//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[182]####
        }//####[182]####
        taskinfo.setQueueArgIndexes(1);//####[182]####
        taskinfo.setIsPipeline(true);//####[182]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[182]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[182]####
        taskinfo.setInstance(this);//####[182]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[182]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[182]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[182]####
        // ensure Method variable is set//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[182]####
        }//####[182]####
        taskinfo.setQueueArgIndexes(1);//####[182]####
        taskinfo.setIsPipeline(true);//####[182]####
        taskinfo.setTaskIdArgIndexes(0);//####[182]####
        taskinfo.addDependsOn(currentNode);//####[182]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[182]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[182]####
        taskinfo.setInstance(this);//####[182]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[182]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[182]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[182]####
    }//####[182]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[182]####
        // ensure Method variable is set//####[182]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[182]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[182]####
        }//####[182]####
        taskinfo.setQueueArgIndexes(0, 1);//####[182]####
        taskinfo.setIsPipeline(true);//####[182]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[182]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[182]####
        taskinfo.setInstance(this);//####[182]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[182]####
    }//####[182]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[182]####
        recursiveMethod(currentNode, nodesToCheck, true);//####[183]####
        semaphore++;//####[184]####
    }//####[186]####
//####[186]####
//####[188]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[188]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[191]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[192]####
        String newNodeName = nodeToCheckStr;//####[195]####
        int newProcessor = processorNumber;//####[196]####
        int nodalWeight = getNodalWeight(newNodeName);//####[197]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[198]####
        int newEndTime = newStartTime + nodalWeight;//####[199]####
        processorArray[newProcessor] = newEndTime;//####[200]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[203]####
        return nextNode;//####[204]####
    }//####[205]####
}//####[205]####
