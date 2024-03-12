//package br.com.api.productadministration.configuration;
//
//import br.com.api.productadministration.categories.CategoryIntegrationTest;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.jupiter.api.extension.TestWatcher;
//
//
//public class LoggerExtension implements TestWatcher {
//
//  private static final Logger LOG = LogManager.getLogger(CategoryIntegrationTest.class);
//
//  @Override
//  public void testFailed(final ExtensionContext context, final Throwable cause) {
//    LOG.error(String.format("TEST_FAILED='%s' exceptionMessage='%s'", context.getDisplayName(), cause.getMessage()));
//    TestWatcher.super.testFailed(context, cause);
//  }
//
//  @Override
//  public void testSuccessful(final ExtensionContext context) {
//    LOG.info(String.format("TEST_SUCCESSFUL='%s'", context.getDisplayName()));
//    TestWatcher.super.testSuccessful(context);
//  }
//}
