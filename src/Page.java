import java.util.BitSet;

public class Page
{
  public int id;
  public int physical;
  public byte R;
  public byte M;
  public int inMemTime;
  public int lastTouchTime;
  public long high;
  public long low;

  private long pageCounter;
  private static int numberOfBitsInCounter = 8;

  public Page( int id, int physical, byte R, byte M, int inMemTime, int lastTouchTime, long high, long low ) 
  {
    this.pageCounter = 0;
    this.id = id;
    this.physical = physical;
    this.R = R;
    this.M = M;
    this.inMemTime = inMemTime;
    this.lastTouchTime = lastTouchTime;
    this.high = high;
    this.low = low;
  }

  public void clear_counter(){
    pageCounter = 0;
  }

  public void right_shift_counter(){
    if(this.R == 1)
      this.pageCounter = (this.pageCounter>>1)|(long)Math.pow(2,numberOfBitsInCounter-1);
    else
      this.pageCounter = this.pageCounter>>1;
  }

  public void setLimitForCounter(int numberOfBitsInCounter){
    this.numberOfBitsInCounter = numberOfBitsInCounter;
  }

  public long getPageCounter(){
    return pageCounter;
  }
}
