/*
 * Returned from the DirectoryCompare tool. Contains details of the result of the comparison. 
 */
package DirCompareTool;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author Marshall
 */
public class CompareResult
{

    private ArrayList<Path> matches;
    private ArrayList<Path> discrepancies;
    private ArrayList<Path> missing;

    public CompareResult()
    {
        matches = new ArrayList<>();
        discrepancies = new ArrayList<>();
        missing = new ArrayList<>();
    }

    public ArrayList<Path> getMatches()
    {
        return matches;
    }

    public ArrayList<Path> getDiscrepancies()
    {
        return discrepancies;
    }

    public ArrayList<Path> getMissing()
    {
        return missing;
    }

    void addMatch(Path p)
    {
        matches.add(p);
    }

    void addDiscrepancy(Path p)
    {
        discrepancies.add(p);
    }
    
    void addMissing(Path p)
    {
        missing.add(p);
    }

    /**
     * Returns a number representing the proportion of matching files
     *
     * @return double, from 0 to 1, with 1 representing a complete match
     */
    public double getAmountMatched()
    {
        double matchSize = matches.size();
        double totalSize = matchSize + discrepancies.size() + missing.size();

        return (matchSize / totalSize);
    }
}
