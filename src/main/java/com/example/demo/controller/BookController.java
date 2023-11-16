package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Image;
import com.example.demo.entity.common.CommonResponse;
import com.example.demo.entity.common.StatusCode;
import com.example.demo.service.BookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @RequestMapping(
            value = {"/books"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<CommonResponse> getAllBooks() {
        var books = bookService.getAllBooks();
        if (books.isEmpty()) {
            var response = CommonResponse.builder()
                    .code(StatusCode.NOT_FOUND)
                    .message("not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        var response = CommonResponse.builder()
                .code(StatusCode.SUCCESS)
                .message("SUCCESS")
                .data(books)
                .build();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(
            value = {"/book/{id}"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<CommonResponse> getBookDetail(@PathVariable Integer id) {
        var book = bookService.getBookDetail(id);
        if (book.isPresent()) {
            var response = CommonResponse.builder()
                    .code(StatusCode.SUCCESS)
                    .message("SUCCESS")
                    .data(book)
                    .build();
            return ResponseEntity.ok(response);
        }
        var response = CommonResponse.builder()
                .code(StatusCode.NOT_FOUND)
                .message("not found")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @RequestMapping(
            value = {"/book"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<CommonResponse> createBook(@RequestBody Book data) {
        try {
//            var prepare = prepareBook();
            var book = bookService.createBook(data);
            var response = CommonResponse.builder()
                    .code(StatusCode.SUCCESS)
                    .message("SUCCESS")
                    .data(book)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            var response = CommonResponse.builder()
                    .code(StatusCode.INTERNAL_ERROR)
                    .message("Internal Server Error")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    private Book prepareBook() {
        var author = new Author();
        author.setName("Lê AnNh");
        author.setSex(false);
        author.setAddress("Lã Xuân Oai");
        var image = new Image();
        image.setTitle("Cánh Đồng Bất Tận");
        image.setPath("/image/Canh_Dong_Bat_Tan.jpg");
        var book = new Book();
        book.setAuthor(author);
        book.setImage(image);
        book.setTitle("Cánh đồng bất tận");
        book.setPublicationName("NXB Kim Đồng");
        book.setPublicationYear("2023");
        book.setContent("Cánh đồng bất tận bao gồm những truyện hay và mới nhất của nhà văn Nguyễn Ngọc Tư. Đây là tác phẩm đang gây xôn xao trong đời sống văn học, bởi ở đó người ta tìm thấy sự dữ dội, khốc liệt của đời sống thôn dã qua cái nhìn của một cô gái. Bi kịch về nỗi mất mát, sự cô đơn được đẩy lên đến tận cùng, khiến người đọc có lúc cảm thấy nhói");
        return book;
    }
}
