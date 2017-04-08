package william.anderson;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by TsarVadim on 4/8/2017.
 */

@DynamoDBTable(tableName = "ItemName")
public class Items {
    private String name;
    private String list;

    @DynamoDBHashKey(attributeName = "ItemId")
    public String getItemId() {
        return name;
    }

    public void setItemId(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "ListName")
    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}