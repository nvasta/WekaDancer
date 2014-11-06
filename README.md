WekaDancer
==========
Utilising the Weka learning library for the DANCER Project


## Outline      
This project is using the Weka machine learning library to find patterns
in the everyday operation performed by people in their daily life. The
data used, should be imported before hand in an InfluxDB library. The 
data for each one case, comprise of two time series, the context and the 
actions.

### The Context
This InfluxDB series contains the data from sensors along with their
timestamps. To make their processing easier, all data are included with
a single timestamp. If a reading is non existent for a specific time,
the 0 or NaN can be set a the value. The identifier of each sensor, is
set as the column header for the respective values.

### The Actions
The actions are stored in a separate time series with the same name as the
context one, plus the suffix '.actions.'. The format of this time series is 
the following:
  time | sequence_number | action | device | parameters | source


## Installation     
WekaDancer is build on the Eclipse IDE, so you can just download the project 
as a zip or clone the git repository. Then open Eclipse and import the 
download as an existing project:
1. Select the root directory if you clone the git repository
or
2. Select the archive file if you downloaded as a zip


## Configuration    
Multiple libraries are required so load Weka and this is why these have
been included in the repository, under the lib/ directory.

However, in order to run the project the correct java compiler should
be set in the Eclipse project configuration. This should be 1.7, although
both Sun and OpenJRE should work.

It is also compatible with version 1.8 but further testing is needed and
minor problems might appear.


## Running        
To run the project a set of argument should be given. An example is of the
required one is as follows:
WekaDancer [-h InfluxDB_host] 
           [-d InfluxDB_database]
           [-s InfluxDB_series]
           [-f ARFF_filename]
           [-a algorithm]
           [-b begin_timestamp]
           [-e end_timestamp]
