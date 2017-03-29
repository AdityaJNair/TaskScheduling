# RunningGoatShorts
Task Parallelization

AdityaJNair = Aditya Nair

PriyankitSingh = Priyankit Singh

JSCooke = Jayden Cooke

JunoJin = Juno Jin

Wombotastic = Nathan Situ

The following program was built on JRE 1.8

Instructions for running this program on the bash command line:

(1) Download scheduler.jar, and place it into the directory of your choice.

(2) Open a terminal and navigate to the directory.

(3) If your machine uses JRE 1.8 by default, execute 

                java -jar scheduler.jar "input graph" "processor count" "[OPTIONS]"
    
Instructions for starting up program on your IDE:

(1) Download the RunningGoatShorts folder into your workspace.

(2) Create a new Java Project called RunningGoatShorts, make sure the name is correct or the files will not be visible.

(3) There will be library errors, right click on Referenced Library -> build path -> configure build path and add the jar files in 306 Project/src/resources:
                gs-core-1.3.jar
                gs-algo-1.3.jar
                gs-ui-1.3.jar
                PTRuntime.jar

(4) Go on run configurations and on the arguments tab type in:
                "input graph" "processor count" "[OPTIONS]"
  
(5) Run it.

Details on parameters:
As specified in the assignment brief.

Mandatory Parameters:

Input graph: The path to the input graph, stored as a .dot file. The code is specifically designed for directed acyclic graphs, and may have unpredicatable results  in other cases.

Processor count: The number of processors on which to schedule the tasks.

Optional Parameters:

-p N: Run in parallel, using up to N threads. Without this, the code will be run in sequential.

-v: Display a visual representation of the search in a JFrame.

-o "OUTPUT": The generated output file will be named "OUTPUT.dot". If not specified, the input graph name will be used to generate a name (INPUT-output.dot). 


The [wiki](https://github.com/AdityaJNair/RunningGoatShorts/wiki) contains all information regarding previous meetings, plans, decisions, background research etc.
  
                
                
