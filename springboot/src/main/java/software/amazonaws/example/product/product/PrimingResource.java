/**
This code is used to prime the resources in SnapStart example. This file can be excluded if not using SnapStart.
 To learn more about SnapStart and Priming, refer to https://aws.amazon.com/blogs/compute/reducing-java-cold-starts-on-aws-lambda-functions-with-snapstart/
 **/

package software.amazonaws.example.product.product;

import org.crac.Context;
import org.crac.Core;
import org.crac.Resource;
import org.springframework.context.annotation.Configuration;
import software.amazonaws.example.product.product.dao.ProductDao;

@Configuration
public class PrimingResource implements Resource {

  private final ProductDao productDao;

  public PrimingResource(ProductDao productDao) {
    this.productDao = productDao;
    Core.getGlobalContext().register(this);
  }

  @Override
  public void beforeCheckpoint(Context<? extends Resource> context) throws Exception {
    System.out.println("beforeCheckpoint hook");
    //This call to getProduct will initialize the AWS DynamoDb SDK before the snapshot is taken. This technique is called Priming.
    productDao.getProduct("1");
  }

  @Override
  public void afterRestore(Context<? extends Resource> context) throws Exception {
    System.out.println("afterRestore hook");
  }
}
