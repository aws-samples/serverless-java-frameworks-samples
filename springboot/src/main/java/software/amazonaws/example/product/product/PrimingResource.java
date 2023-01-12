/**
 * This code is used to prime the resources in SnapStart example. This file can be excluded if not using SnapStart.
 * To learn more about SnapStart and Priming, refer to https://aws.amazon.com/blogs/compute/reducing-java-cold-starts-on-aws-lambda-functions-with-snapstart/
 **/

package software.amazonaws.example.product.product;

import org.crac.Context;
import org.crac.Core;
import org.crac.Resource;
import org.springframework.context.annotation.Configuration;
import software.amazonaws.example.product.product.dao.DynamoProductDao;

@Configuration
public class PrimingResource implements Resource {

  private final DynamoProductDao productDao;

  public PrimingResource(DynamoProductDao productDao) {
    this.productDao = productDao;
    Core.getGlobalContext().register(this);
  }

  @Override
  public void beforeCheckpoint(Context<? extends Resource> context) throws Exception {
    System.out.println("beforeCheckpoint hook");
    //Below line would initialize the AWS SDK DynamoDBClient class. This technique is called "Priming".
    productDao.describeTable();
  }

  @Override
  public void afterRestore(Context<? extends Resource> context) throws Exception {
    System.out.println("afterRestore hook");
  }
}
