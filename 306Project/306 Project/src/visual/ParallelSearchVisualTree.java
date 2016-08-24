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
            String procText = new String();//####[41]####
            ArrayList procList = new ArrayList();//####[42]####
            for (NodeObject node : currentNode.getCurrentPath()) //####[43]####
            {//####[43]####
                int processor = node.getProcessor();//####[44]####
                if (!procList.contains(processor)) //####[45]####
                {//####[45]####
                    procList.add(processor);//####[46]####
                    procText += "[" + Integer.toString(processor + 1) + "] ";//####[47]####
                }//####[48]####
            }//####[49]####
            String idleText = new String();//####[50]####
            for (int i = 0; i < numberofProcessors; i++) //####[51]####
            {//####[51]####
                if (!procList.contains(i)) //####[52]####
                {//####[52]####
                    idleText += "[" + Integer.toString(i + 1) + "] ";//####[53]####
                }//####[54]####
            }//####[55]####
            if (idleText.isEmpty()) //####[56]####
            {//####[56]####
                idleText = "None";//####[57]####
            }//####[58]####
            processorsUsedLabel.setText("Processors Used: " + procText);//####[59]####
            idleProcessorsLabel.setText("Idle Processors: " + idleText);//####[60]####
            String currentValidSchedule = new String();//####[62]####
            for (NodeObject node : currentNode.getCurrentPath()) //####[63]####
            {//####[63]####
                if (!(node.getNodeName() == "rootNode")) //####[64]####
                {//####[64]####
                    currentValidSchedule += node.getNodeName() + "(" + (node.getProcessor() + 1) + ")";//####[65]####
                }//####[66]####
            }//####[67]####
            validScheduleLabel.setText("Current Valid Schedule: " + currentValidSchedule);//####[69]####
            if (maxTimeAtPoint(currentNode) < minimumTime) //####[73]####
            {//####[73]####
                bestTimeCount++;//####[75]####
                for (Edge e : bestTimeTree.getEdgeSet()) //####[78]####
                {//####[78]####
                    e.addAttribute("ui.style", "fill-color:#ADB5C7;");//####[79]####
                }//####[80]####
                minimumTime = maxTimeAtPoint(currentNode);//####[81]####
                bestTimeLabel.setText("Current Best Time: " + maxTimeAtPoint(currentNode));//####[82]####
                bestTimeCountLabel.setText("Faster Schedules Found: " + Long.toString(bestTimeCount));//####[83]####
                bestSchedule = currentNode.getCurrentPath();//####[84]####
                int[] endArray = endArray(bestSchedule);//####[85]####
                String endArrayString = new String();//####[86]####
                int procID = 1;//####[87]####
                for (int eA : endArray) //####[88]####
                {//####[88]####
                    endArrayString += Integer.toString(procID) + ": " + eA + " ";//####[89]####
                    procID++;//####[90]####
                }//####[91]####
                processorEndTimeLabel.setText("Processor End Time: " + endArrayString);//####[92]####
                String oldNode = new String();//####[93]####
                String newNode = new String();//####[94]####
                int i = 0;//####[95]####
                Edge e;//####[96]####
                String bestPath = new String();//####[97]####
                for (NodeObject node : currentNode.getCurrentPath()) //####[98]####
                {//####[98]####
                    if (node.getNodeName().equals("rootNode")) //####[99]####
                    continue;//####[100]####
                    newNode += node.getNodeName() + node.getProcessor();//####[101]####
                    if (bestTimeTree.getNode(newNode) == null) //####[102]####
                    {//####[102]####
                        Node n = bestTimeTree.addNode(newNode);//####[103]####
                        n.addAttribute("ui.label", node.getNodeName() + "(" + (node.getProcessor() + 1) + ")");//####[105]####
                        n.addAttribute("layout.frozen");//####[107]####
                        n.addAttribute("y", -i * 15);//####[109]####
                        if (i == 0) //####[110]####
                        n.addAttribute("x", nid);//####[111]####
                        if (i > 0) //####[112]####
                        {//####[112]####
                            if ((int) bestTimeTree.getNode(oldNode).getAttribute("x") < 0) //####[113]####
                            {//####[113]####
                                n.addAttribute("x", -java.lang.Math.abs(nid));//####[114]####
                            } else {//####[115]####
                                n.addAttribute("x", java.lang.Math.abs(nid));//####[116]####
                            }//####[117]####
                        }//####[118]####
                    }//####[119]####
                    if (i > 0) //####[120]####
                    {//####[120]####
                        bestTimeTree.removeEdge(newNode, oldNode);//####[121]####
                        e = bestTimeTree.addEdge(Integer.toString(nid), newNode, oldNode);//####[122]####
                        e.setAttribute("ui.style", "fill-color:red;");//####[123]####
                    }//####[125]####
                    oldNode = newNode;//####[127]####
                    i++;//####[128]####
                    if (nid < 0) //####[129]####
                    nid--; else nid++;//####[130]####
                    try {//####[134]####
                        Thread.sleep(50);//####[135]####
                    } catch (Exception E2) {//####[136]####
                    }//####[138]####
                    bestPath += node.getNodeName() + "(" + (node.getProcessor() + 1) + ") ";//####[140]####
                }//####[141]####
                bestTimeScheduleLabel.setText("Best Path: " + bestPath);//####[142]####
                nid *= -1;//####[143]####
            } else if (maxTimeAtPoint(currentNode) == minimumTime) //####[144]####
            {//####[144]####
                equalBestTimeCount++;//####[145]####
                equalBestTimeCountLabel.setText("Duplicate Schedules at Current Optimal Time Found: " + Long.toString(equalBestTimeCount));//####[146]####
            }//####[147]####
            return;//####[148]####
        }//####[149]####
        if (minimumTime != Integer.MAX_VALUE) //####[152]####
        {//####[152]####
            if (maxTimeAtPoint(currentNode) >= minimumTime) //####[154]####
            {//####[154]####
                return;//####[155]####
            }//####[156]####
            if (calculateLowerBound(currentNode, nodesToCheck) >= minimumTime) //####[158]####
            {//####[158]####
                return;//####[159]####
            }//####[160]####
        }//####[161]####
        TaskID newTask = null;//####[163]####
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
                    NodeObject newNode = createNextNode(currentNode, nodeToCheckStr, j);//####[177]####
                    List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);//####[180]####
                    newUpdatedListWithoutCurrentNode.remove(nodeToCheckStr);//####[181]####
                    nodeNumber++;//####[182]####
                    nodesSearchedLabel.setText("Nodes Searched: " + Long.toString(nodeNumber));//####[183]####
                    semaphoreLabel.setText("Current Threads Used: " + semaphore);//####[184]####
                    if (CurrentTask.insideTask()) //####[185]####
                    {//####[185]####
                        System.out.println(CurrentTask.currentThreadID());//####[186]####
                    }//####[187]####
                    if (isSubtask) //####[188]####
                    {//####[188]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, true);//####[189]####
                        continue;//####[190]####
                    }//####[191]####
                    if (semaphore == 0) //####[193]####
                    {//####[193]####
                        recursiveMethod(newNode, newUpdatedListWithoutCurrentNode, false);//####[194]####
                    } else {//####[195]####
                        semaphore--;//####[196]####
                        newTask = intermediateMethod(newNode, newUpdatedListWithoutCurrentNode);//####[197]####
                        try {//####[198]####
                            newTask.waitTillFinished();//####[199]####
                        } catch (Exception e) {//####[200]####
                        }//####[202]####
                        taskGroup.add(newTask);//####[203]####
                    }//####[204]####
                }//####[206]####
            }//####[207]####
        }//####[208]####
        if (isSubtask) //####[209]####
        {//####[209]####
            return;//####[210]####
        }//####[211]####
        try {//####[212]####
            taskGroup.waitTillFinished();//####[213]####
        } catch (Exception e) {//####[214]####
            e.printStackTrace();//####[215]####
        }//####[216]####
    }//####[217]####
//####[219]####
    private static volatile Method __pt__intermediateMethod_NodeObject_ListString_method = null;//####[219]####
    private synchronized static void __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet() {//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            try {//####[219]####
                __pt__intermediateMethod_NodeObject_ListString_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__intermediateMethod", new Class[] {//####[219]####
                    NodeObject.class, List.class//####[219]####
                });//####[219]####
            } catch (Exception e) {//####[219]####
                e.printStackTrace();//####[219]####
            }//####[219]####
        }//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[219]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[219]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[219]####
        // ensure Method variable is set//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[219]####
        }//####[219]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[219]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[219]####
        taskinfo.setInstance(this);//####[219]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck) {//####[219]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[219]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[219]####
        // ensure Method variable is set//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[219]####
        }//####[219]####
        taskinfo.setTaskIdArgIndexes(0);//####[219]####
        taskinfo.addDependsOn(currentNode);//####[219]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[219]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[219]####
        taskinfo.setInstance(this);//####[219]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck) {//####[219]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[219]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, List<String> nodesToCheck, TaskInfo taskinfo) {//####[219]####
        // ensure Method variable is set//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[219]####
        }//####[219]####
        taskinfo.setQueueArgIndexes(0);//####[219]####
        taskinfo.setIsPipeline(true);//####[219]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[219]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[219]####
        taskinfo.setInstance(this);//####[219]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck) {//####[219]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[219]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[219]####
        // ensure Method variable is set//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[219]####
        }//####[219]####
        taskinfo.setTaskIdArgIndexes(1);//####[219]####
        taskinfo.addDependsOn(nodesToCheck);//####[219]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[219]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[219]####
        taskinfo.setInstance(this);//####[219]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[219]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[219]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[219]####
        // ensure Method variable is set//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[219]####
        }//####[219]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[219]####
        taskinfo.addDependsOn(currentNode);//####[219]####
        taskinfo.addDependsOn(nodesToCheck);//####[219]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[219]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[219]####
        taskinfo.setInstance(this);//####[219]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck) {//####[219]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[219]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, TaskID<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[219]####
        // ensure Method variable is set//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[219]####
        }//####[219]####
        taskinfo.setQueueArgIndexes(0);//####[219]####
        taskinfo.setIsPipeline(true);//####[219]####
        taskinfo.setTaskIdArgIndexes(1);//####[219]####
        taskinfo.addDependsOn(nodesToCheck);//####[219]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[219]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[219]####
        taskinfo.setInstance(this);//####[219]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[219]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[219]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(NodeObject currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[219]####
        // ensure Method variable is set//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[219]####
        }//####[219]####
        taskinfo.setQueueArgIndexes(1);//####[219]####
        taskinfo.setIsPipeline(true);//####[219]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[219]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[219]####
        taskinfo.setInstance(this);//####[219]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[219]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[219]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(TaskID<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[219]####
        // ensure Method variable is set//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[219]####
        }//####[219]####
        taskinfo.setQueueArgIndexes(1);//####[219]####
        taskinfo.setIsPipeline(true);//####[219]####
        taskinfo.setTaskIdArgIndexes(0);//####[219]####
        taskinfo.addDependsOn(currentNode);//####[219]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[219]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[219]####
        taskinfo.setInstance(this);//####[219]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck) {//####[219]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[219]####
        return intermediateMethod(currentNode, nodesToCheck, new TaskInfo());//####[219]####
    }//####[219]####
    private TaskID<Void> intermediateMethod(BlockingQueue<NodeObject> currentNode, BlockingQueue<List<String>> nodesToCheck, TaskInfo taskinfo) {//####[219]####
        // ensure Method variable is set//####[219]####
        if (__pt__intermediateMethod_NodeObject_ListString_method == null) {//####[219]####
            __pt__intermediateMethod_NodeObject_ListString_ensureMethodVarSet();//####[219]####
        }//####[219]####
        taskinfo.setQueueArgIndexes(0, 1);//####[219]####
        taskinfo.setIsPipeline(true);//####[219]####
        taskinfo.setParameters(currentNode, nodesToCheck);//####[219]####
        taskinfo.setMethod(__pt__intermediateMethod_NodeObject_ListString_method);//####[219]####
        taskinfo.setInstance(this);//####[219]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[219]####
    }//####[219]####
    public void __pt__intermediateMethod(NodeObject currentNode, List<String> nodesToCheck) {//####[219]####
        recursiveMethod(currentNode, nodesToCheck, true);//####[220]####
        semaphore++;//####[221]####
    }//####[223]####
//####[223]####
//####[225]####
    private NodeObject createNextNode(NodeObject currentNode, String nodeToCheckStr, int processorNumber) {//####[225]####
        ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());//####[228]####
        int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);//####[229]####
        String newNodeName = nodeToCheckStr;//####[232]####
        int newProcessor = processorNumber;//####[233]####
        int nodalWeight = getNodalWeight(newNodeName);//####[234]####
        int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);//####[235]####
        int newEndTime = newStartTime + nodalWeight;//####[236]####
        processorArray[newProcessor] = newEndTime;//####[237]####
        NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);//####[240]####
        return nextNode;//####[241]####
    }//####[242]####
}//####[242]####
