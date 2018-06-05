/**
 * Command-line interface Main method for directory comparison
 */
package DirCompareTool;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marshall
 */
public class MainCommandLine
{
    public static void main(String[] args)
    {
        FileSystem fileSystem = FileSystems.getDefault();
        
        Path dir1 = null;
        Path dir2 = null;
        int depth = 0;
        
        Iterator<String> i = new Iterator<String>() {
            int curIndex = 0;
            
            @Override
            public boolean hasNext()
            {
                return (curIndex < args.length);
            }

            @Override
            public String next()
            {
                return args[curIndex++];
            }
        };
        
        // TODO: Input verification
        
        while (i.hasNext())
        {
            String curArg = i.next();
            switch (curArg)
            {
                case ("--source"):
                case ("-s"): dir1 = fileSystem.getPath(i.next()); break;
                case ("--target"):
                case ("-t"): dir2 = fileSystem.getPath(i.next()); break;
                case ("--depth"):
                case ("-d"): depth = Integer.parseInt(i.next()); break;
            }
        }
        
        if (dir1 == null || dir2 == null)
        {
            // TODO: Help text
            return;
        }
        
        try
        {
            CompareResult result = DirectoryCompare.compare(dir1, dir2, depth);
            String returnString = String.format("Directory comparison between following two paths:%n%s%n%s%n", dir1.toString(), dir2.toString());
            switch (depth)
            {
                case (0): returnString += String.format("Depth level 0: comparing filenames only%n"); break;
                case (1): returnString += String.format("Depth level 1: comparing filenames and file sizes%n"); break;
                case (2): returnString += String.format("Depth level 2: comparing filenames, file sizes and MD5 hashes%n"); break;
            }
            double amountMatched = result.getAmountMatched();
            returnString += String.format("%d%% match%n", (int)(amountMatched*100));
            ArrayList<Path> matches = result.getMatches();
            ArrayList<Path> discrepancies = result.getDiscrepancies();
            ArrayList<Path> missing = result.getMissing();
            returnString += String.format("%d matches, %d differences, %d missing%n", matches.size(), discrepancies.size(), missing.size());
            if (discrepancies.size() > 0)
            {
                returnString += String.format("Differing files%n-----%n");
                for (Path p : discrepancies)
                {
                    returnString += String.format("%s%n", p.toString());
                }
                returnString += String.format("-----%n");
            }
            if (missing.size() > 0)
            {
                returnString += String.format("Missing files%n-----%n");
                for (Path p : missing)
                {
                    returnString += String.format("%s%n", p.toString());
                }
                returnString += String.format("-----%n");
            }
            System.out.println(returnString);
            
        } catch (IOException ex)
        {
            Logger.getLogger(MainCommandLine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
