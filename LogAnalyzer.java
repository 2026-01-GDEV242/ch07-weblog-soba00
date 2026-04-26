/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 * 
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object LogAnalyzer to analyze hourly web accesses using demoData.txt 
     * as default.
     * 
     */
    public LogAnalyzer()
    {
        this("demoData.txt"); //acts as default fallback behavior using demoData.txt
    }
    
    
    /**
     * Create an object LogAnalyzer using a specific file
     * @param filename name of lof file to be analyized
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
    }
    
    /**
     * loops through access log and counts how many appear, reseting reader for next use
     * 
     * @return total nuber of access log entries
     */
    public int numberOfAccesses()
    {
        int i = 0; //starts the count at zero
        reader.reset();         //adds reset to avoid iterator(LogfileReader) starting from previous index
        while(reader.hasNext()) //will loop until the end (iterator detects no next item)
        {
            reader.next();
            i++;                //moves to next and ups the count by one
        }
        
        return i; //returns the count
    }
    
    
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    
    /**
     * Finds hour with highest number of access entries out of the whole log file
     * 
     * @return busiest hour in log file
     */
    public int busiestHour()
    {
        int store = 0;  //stores the index of the busiest hour
        int toBeat = 0; //stores the current highest number starting at zero cause no negatives
        int i = 0;
        for (int count : hourCounts)
        {
            
            if(count > toBeat)
            {
                toBeat = count;
                store = i;
            }
            i++;    //yeahhh i shouldve just use a regular forloop lol
        }
        
        return store;
    }
    
    /**
     * Finds hour with lowest number of access entries out of the whole log file
     * 
     * @return quietest hour in log file
     */
    public int quietestHour()
    {
        int store = 0;  //stores the index of the busiest hour
        int toBeat = hourCounts[0]; //stores the current lowest number starting at zero cause no negatives

        for (int i = 1; i< hourCounts.length; i++) //learned my lesson
        {
            //checks if current hour is queiter than current hour to beat
            if(hourCounts[i] < toBeat)            
            {
                toBeat = hourCounts[i];
                store = i;
            }
        }
        
        return store;
    }
    
    /**
     * Finds the two hour stretch with the hights total number of access entries and
     * returns the starting hour
     * 
     * @return the fist hour of the busiest two hour stretch
     */
    public int busiestTwoHour()
    {
        int store = 0;  //stores the index of the first hour the busiest pair
        int toBeat = hourCounts[0] + hourCounts[1]; //stores the current highest number starting at zero cause no negatives
        int comboCount = 0;
        for (int i = 0; i< hourCounts.length-1; i++) //learned my lesson
        {
            comboCount = hourCounts[i]+hourCounts[i+1];
            
            if(comboCount > toBeat)            
            {
                toBeat = comboCount;
                store = i;
            }
        }
        
        return store;
    }
    
    
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
