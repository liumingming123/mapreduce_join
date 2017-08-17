package com.test.join;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by 小新很忙 on 2017/8/16.
 */
public class CustomMapKey implements WritableComparable<CustomMapKey> {
    private int cusId;
    private int order;

    public CustomMapKey() {

    }

    public CustomMapKey(int cusId, int order) {
        this.cusId = cusId;
        this.order = order;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int compareTo(CustomMapKey o) {
        int res=cusId>=o.cusId?1:-1;
        return res==0?(order>=o.order?1:-1):res;
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(cusId);
        dataOutput.writeInt(order);
    }

    public void readFields(DataInput dataInput) throws IOException {
      cusId=  dataInput.readInt();
      order=dataInput.readInt();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  CustomMapKey){
            CustomMapKey o=(CustomMapKey)obj;
            return cusId==o.cusId&&order==o.order;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return cusId+" "+order;
    }
}
