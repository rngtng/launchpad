import themidibus.*;

import com.rngtng.launchpad.*;

import jklabs.monomic.*;

Monome m;

m = new MonomeLaunchpad(this);	

m.setDebug(true);

m.lightsOn();
m.lightsOff();

m.lightOn(0,0);
m.lightOff(0,0);

m.setValue(0,0,1);
m.setValue(0,0,0);

int[] vals = new int[]{0,0,1,1,0,0,1,1};

byte bitVals = 0x33;

m.setRow(1,vals);
m.setCol(2,bitVals);

int[][] matrix = new int[][]{
                  {0,1,0,1,0,1,0,1},
                  {1,0,1,0,1,0,1,0},
                  {0,1,0,1,0,1,0,1},
                  {1,0,1,0,1,0,1,0},
                  {0,1,0,1,0,1,0,1},
                  {1,0,1,0,1,0,1,0},
                  {0,1,0,1,0,1,0,1},
                  {1,0,1,0,1,0,1,0},
                };

m.setValues(matrix);
                
byte[] bitMatrix = new byte[]{0x0f, 0x0f, 0x0f, 0x0f,0x0f, 0x0f, 0x0f, 0x0f};

m.setValues(bitMatrix);
