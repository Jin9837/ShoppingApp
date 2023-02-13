package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.ProductWatchListDao;
import com.example.shoppingapp.domain.entity.ProductWatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@EnableTransactionManagement
public class ProductWatchListService {

    private ProductWatchListDao productWatchListDao;

    @Autowired
    public ProductWatchListService(ProductWatchListDao productWatchListDao) {
        this.productWatchListDao = productWatchListDao;
    }


    @Transactional
    public void addToProductWatchList(int userId, int productId){
        productWatchListDao.addToProductWatchList(userId, productId);
    }

    @Transactional
    public void removeFromProductWatchList(int userId, int productId) throws EntityNotFoundException {
        productWatchListDao.removeFromProductWatchList(userId, productId);
    }


    @Transactional
    public List<ProductWatchList> getAllProductWatchList(int userId){
        return productWatchListDao.getAllProductWatchList(userId);
    }

}
