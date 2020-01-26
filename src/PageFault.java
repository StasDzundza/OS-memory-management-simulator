/* It is in this file, specifically the replacePage function that will
   be called by MemoryManagement when there is a page fault.  The 
   users of this program should rewrite PageFault to implement the 
   page replacement algorithm.
*/

  // This PageFault file is an example of the FIFO Page Replacement 
  // Algorithm as described in the Memory Management section.

import java.util.*;

public class PageFault {

  /**
   * The page replacement algorithm for the memory management sumulator.
   * This method gets called whenever a page needs to be replaced.
   * <p>
   * The page replacement algorithm included with the simulator is 
   * FIFO (first-in first-out).  A while or for loop should be used 
   * to search through the current memory contents for a canidate 
   * replacement page.  In the case of FIFO the while loop is used 
   * to find the proper page while making sure that virtPageNum is 
   * not exceeded.
   * <pre>
   *   Page page = ( Page ) mem.elementAt( oldestPage )
   * </pre>
   * This line brings the contents of the Page at oldestPage (a 
   * specified integer) from the mem vector into the page object.  
   * Next recall the contents of the target page, replacePageNum.  
   * Set the physical memory address of the page to be added equal 
   * to the page to be removed.
   * <pre>
   *   controlPanel.removePhysicalPage( oldestPage )
   * </pre>
   * Once a page is removed from memory it must also be reflected 
   * graphically.  This line does so by removing the physical page 
   * at the oldestPage value.  The page which will be added into 
   * memory must also be displayed through the addPhysicalPage 
   * function call.  One must also remember to reset the values of 
   * the page which has just been removed from memory.
   *
   * @param mem is the vector which contains the contents of the pages 
   *   in memory being simulated.  mem should be searched to find the 
   *   proper page to remove, and modified to reflect any changes.  
   * @param virtPageNum is the number of virtual pages in the 
   *   simulator (set in Kernel.java).  
   * @param replacePageNum is the requested page which caused the 
   *   page fault.  
   * @param controlPanel represents the graphical element of the 
   *   simulator, and allows one to modify the current display.
   */

  public static void replacePage ( Vector mem , int virtPageNum , int replacePageNum , ControlPanel controlPanel ) 
  {
    int count = 0;
    int oldestPage = -1;
    int oldestTime = 0;
    int firstPage = -1;
    int map_count = 0;
    boolean mapped = false;

    while ( ! (mapped) || count != virtPageNum ) {
      Page page = ( Page ) mem.elementAt( count );
      if ( page.physical != -1 ) {
        if (firstPage == -1) {
          firstPage = count;
        }
        if (page.inMemTime > oldestTime) {
          oldestTime = page.inMemTime;
          oldestPage = count;
          mapped = true;
        }
      }
      count++;
      if ( count == virtPageNum ) {
        mapped = true;
      }
    }
    if (oldestPage == -1) {
      oldestPage = firstPage;
    }
    Page page = ( Page ) mem.elementAt( oldestPage );
    Page nextpage = ( Page ) mem.elementAt( replacePageNum );
    controlPanel.removePhysicalPage( oldestPage );
    nextpage.physical = page.physical;
    controlPanel.addPhysicalPage( nextpage.physical , replacePageNum );
    page.inMemTime = 0;
    page.lastTouchTime = 0;
    page.R = 0;
    page.M = 0;
    page.physical = -1;
  }

  //Aging algorithm (Алгоритм старіння)
  public static void replacePageByAgingAlgorithm(Vector mem , int virtPageNum , int replacePageNum , ControlPanel controlPanel){
    int max_number_of_zeroes = -1;
    int outdated_page_index = 0;
    for(int i = 0; i < virtPageNum; i++){
      Page current_page = ( Page ) mem.elementAt(i);
      if(current_page.physical!=-1) {
        int k = 0;
        int number_of_zeroes = 0;
        while (true) {
          if (current_page.page_counter[k] == 0 && k != current_page.page_counter.length - 1) {
            number_of_zeroes++;
          } else if (current_page.page_counter[k] == 1 || k == current_page.page_counter.length - 1) {
            break;
          }
          k++;
        }
        if (number_of_zeroes > max_number_of_zeroes) {
          max_number_of_zeroes = number_of_zeroes;
          outdated_page_index = i;
        }
      }
    }

    //all counters is filled by ones
    if(max_number_of_zeroes == -1){
        for(int i = 0; i < virtPageNum; i++) {
            Page current_page = (Page) mem.elementAt(i);
            if(current_page.physical!=-1){
                outdated_page_index = i;
                break;
            }
        }
    }
    Page outdated_page = ( Page ) mem.elementAt( outdated_page_index );
    Page new_page = ( Page ) mem.elementAt( replacePageNum );
    controlPanel.removePhysicalPage( outdated_page_index );
    new_page.physical = outdated_page.physical;
    controlPanel.addPhysicalPage( new_page.physical , replacePageNum );
    outdated_page.inMemTime = 0;
    outdated_page.lastTouchTime = 0;
    outdated_page.R = 0;
    outdated_page.M = 0;
    outdated_page.physical = -1;
    outdated_page.clear_counter();
  }
}
