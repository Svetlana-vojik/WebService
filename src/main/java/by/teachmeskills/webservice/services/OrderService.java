package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.ProductDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface OrderService {

    OrderDto updateOrder(OrderDto orderDto);

    OrderDto createOrder(OrderDto orderDto);

    void deleteOrder(int id);

    OrderDto getOrderById(int id);

    List<OrderDto> getOrdersByUserId(int id);

    List<ProductDto> getProductByOrderId(int id);

    List<OrderDto> getAllOrders();

    List<OrderDto> importOrdersFromCsv(MultipartFile file);

    void exportOrdersToCsv(HttpServletResponse response, int id) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException;
}