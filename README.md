<h1>Sensor On Bus Problem Metaheuristics Implementation</h1>
<p>This repository contains the implementation of several metaheuristic algorithms to solve the Sensor On Bus Problem (SOBP). The SOBP is a problem that involves selecting the optimal combination of bus routes and turn-on points for air quality monitoring sensors in order to maximize the number of observable critical squares in a grid.</p>
<h2>Problem Description</h2>
<p>The SOBP can be described as follows:</p>
<ul>
	<li>A grid of p×q squares, with each square marked as critical or non-critical.</li>
	<li>A map of n bus routes, each represented by an ordered list of polylines.</li>
	<li>m air quality monitoring sensors to be attached to buses, where each bus can have at most one sensor installed.</li>
	<li>The goal is to choose m bus routes and turn-on points for each sensor installed to maximize the number of observable critical squares.</li>
</ul>
<h2>Implemented Algorithms</h2>
<p>The following metaheuristic algorithms have been implemented in this repository:</p>
<ul>
	<li>Genetic Algorithm (GA)</li>
	<li>Simulated Annealing (SA)</li>
</ul>
<h2>Getting Started</h2>
<p>To use the implementation, clone the repository and run the 
	<code>main.py</code> file. The 
	<code>main.py</code> file contains the main logic for initializing the problem instance, selecting an algorithm, and running the optimization process.
</p>
<p>The 
	<code>config.py</code> file contains configuration options for the problem instance, algorithm selection, and optimization parameters.
</p>

<h2>Getting Started</h2>
<p>To use the implementation, download the 
	<code>SensorOnBusProblem.jar</code> file and run the following command in your terminal:
</p>
<code class="!whitespace-pre hljs">java -jar SensorOnBusProblem.jar
</code>
<p>This will run the program with the default configuration. If you wish to modify the configuration options, you can do so by creating a 
	<code>config.properties</code> file and placing it in the same directory as the 
	<code>SensorOnBusProblem.jar</code> file. The available configuration options are listed in the 
	<code>config.properties</code> file.
</p>
<p>The program will output the results of the optimization process to the console, including the best solution found, the number of observable critical squares, and the time taken to find the solution.</p>
<p>Note: The 
	<code>SensorOnBusProblem.jar</code> file requires Java to be installed on your system.
</p>

<h2>Results</h2>
<p>The 
	<code>results/</code> directory contains the results of running the algorithms on the problem instance. The results include the best solution found, the number of observable critical squares, and the time taken to find the solution.
</p>
