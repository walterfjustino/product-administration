package br.com.api.productadministration.utils;

import br.com.api.productadministration.categories.model.Category;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class CategoryAggregator implements ArgumentsAggregator {
  @Override
  public Object aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) throws ArgumentsAggregationException {
    return new Category(arguments.getLong(0),
            arguments.getString(1),
            arguments.getBoolean(2),
            arguments.getString(3));
  }
}
