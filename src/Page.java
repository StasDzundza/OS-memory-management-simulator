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

  public byte[] page_counter;

  public Page( int id, int physical, byte R, byte M, int inMemTime, int lastTouchTime, long high, long low ) 
  {
    page_counter = new byte[]{0,0,0,0,0,0,0,0};
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
    for(int i = 0; i < page_counter.length; i++)
      page_counter[i] = 0;
  }

  public void right_shift_counter(){
    for (int i = page_counter.length - 1; i >= 1; i--) {
      this.page_counter[i] = this.page_counter[i - 1];
    }
    this.page_counter[0] = this.R;
  }

}
