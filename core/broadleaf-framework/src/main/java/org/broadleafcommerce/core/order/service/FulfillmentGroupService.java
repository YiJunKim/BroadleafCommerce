/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.order.service;

import java.util.List;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupFee;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderMultishipOption;
import org.broadleafcommerce.core.order.service.call.FulfillmentGroupItemRequest;
import org.broadleafcommerce.core.order.service.call.FulfillmentGroupRequest;
import org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

public interface FulfillmentGroupService {

    public FulfillmentGroup save(FulfillmentGroup fulfillmentGroup);

    public FulfillmentGroup createEmptyFulfillmentGroup();

    public FulfillmentGroup findFulfillmentGroupById(Long fulfillmentGroupId);

    public void delete(FulfillmentGroup fulfillmentGroup);
    
    public FulfillmentGroup addFulfillmentGroupToOrder(FulfillmentGroupRequest fulfillmentGroupRequest, boolean priceOrder) throws PricingException;
    
    public FulfillmentGroup addItemToFulfillmentGroup(FulfillmentGroupItemRequest fulfillmentGroupItemRequest, boolean priceOrder) throws PricingException;
    
    public Order removeAllFulfillmentGroupsFromOrder(Order order, boolean priceOrder) throws PricingException;

    /**
     * Removes every fulfillment group item in every fulfillment group in the order
     * that is associated with the given orderItem. Note that it does not save the changes
     * made - instead, the caller is responsible for saving the order further down.
     * 
     * @param order
     * @param orderItem
     */
	public void removeOrderItemFromFullfillmentGroups(Order order, OrderItem orderItem);
	
	public FulfillmentGroupFee createFulfillmentGroupFee();

	/**
	 * Associates FulfillmentGroupItems in the given Order such that they match the structure
	 * of the OrderMultishipOptions associated with the given Order. 
	 * 
	 * @see OrderMultishipOption
	 * 
	 * @param order
	 * @return the saved order
	 * @throws PricingException 
	 */
	public Order matchFulfillmentGroupsToMultishipOptions(Order order, boolean priceOrder) throws PricingException;

	/**
	 * Collapses all of the fulfillment groups in the given order to the first fulfillment group
	 * in the order.
	 * 
	 * @see #matchFulfillmentGroupsToMultishipOptions(Order, boolean)
	 * 
	 * @param order
	 * @param priceOrder
	 * @return the saved order
	 * @throws PricingException 
	 */
	public Order collapseToOneFulfillmentGroup(Order order, boolean priceOrder) throws PricingException;


	/**
	 * Reads FulfillmentGroups whose status is not FULFILLED or DELIVERED.
	 * @param start
	 * @param maxResults
	 * @return
	 */
	public List<FulfillmentGroup> findUnfulfilledFulfillmentGroups(int start, int maxResults);
	
	/**
	 * Reads FulfillmentGroups whose status is PARTIALLY_FULFILLED or PARTIALLY_DELIVERED.
	 * 
	 * @param start
	 * @param maxResults
	 * @return
	 */
	public List<FulfillmentGroup> findPartiallyFulfilledFulfillmentGroups(int start, int maxResults);
	
	/**
	 * Returns FulfillmentGroups whose status is null, or where no processing has yet occured. 
	 * Default returns in ascending order according to date that the order was created.
	 * @param start
	 * @param maxResults
	 * @return
	 */
	public List<FulfillmentGroup> findUnprocessedFulfillmentGroups(int start, int maxResults);
	
	/**
	 * Reads FulfillmentGroups by status, either ascending or descending according to the date that 
	 * the order was created.
	 * @param status
	 * @param start
	 * @param maxResults
	 * @param ascending
	 * @return
	 */
	public List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults, boolean ascending);
	
	/**
	 * Reads FulfillmentGroups by status, ascending according to the date that 
	 * the order was created.
	 * @param status
	 * @param start
	 * @param maxResults
	 * @return
	 */
	public List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults);

}
