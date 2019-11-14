package sgsits.cse.dis.infrastructure.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.infrastructure.model.StockDetails;
import sgsits.cse.dis.infrastructure.repo.StockRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dis") 
@Api(value = "Stock Resource")
public class StockController {

	@Autowired
	StockRepository stockRepository;
	
	@ApiOperation(value = "listStock", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/listSock", method = RequestMethod.GET)
	public List<StockDetails> getAllStockDetails()
	{
		return stockRepository.findAll();
	}
	
	@ApiOperation(value = "addStock", response = Object.class, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/addStock", method = RequestMethod.POST)
	public ResponseEntity<String> addStock(@RequestBody StockDetails stock)
	{
		stock.setCreatedDate(java.time.Clock.systemUTC().instant());
		stockRepository.save(stock);
		return new ResponseEntity<>("Stock has been added!", HttpStatus.OK);
	}	
	
	@ApiOperation(value = "deleteStock", response = Object.class, httpMethod = "DELETE", produces = "application/json")
	@RequestMapping(value = "/deleteStock/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteStock(@PathVariable long id)
	{
		stockRepository.deleteById(id);
		return new ResponseEntity<>("Stock has been deleted!", HttpStatus.OK);
	}
	
	@ApiOperation(value = "updateStock", response = Object.class, httpMethod = "PUT", produces = "application/json")
	@RequestMapping(value = "/updateStock/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateStock(@RequestBody StockDetails stockdetail, @PathVariable long id)
	{
		Optional<StockDetails> stock = stockRepository.findById(id);
		if (!stock.isPresent())
			return new ResponseEntity<>("Stock is not available!", HttpStatus.NOT_FOUND);
		stockdetail.setId(id);
		stockdetail.setModifiedDate(java.time.Clock.systemUTC().instant());
		stockRepository.save(stockdetail);
		return new ResponseEntity<>("Stock Updated Successfully!", HttpStatus.OK);
	}
	
	@ApiOperation(value = "findStock", response = Object.class, httpMethod = "GET", produces = "application/json")
	@RequestMapping(value = "/findStock/{itemName}", method = RequestMethod.GET)
	public List<StockDetails> findByName(@PathVariable String itemName)
	{
		return stockRepository.findByItemName(itemName);
	}
}
