package com.mycompany.authorbookapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.mycompany.authorbookapi.client.BookReviewApiClient;
import com.mycompany.authorbookapi.client.BookReviewApiQueryBuilder;
import com.mycompany.authorbookapi.client.BookReviewApiResult;
import com.mycompany.authorbookapi.graphql.service.AuthorService;
import com.mycompany.authorbookapi.model.Author;
import com.mycompany.authorbookapi.model.Book;
import com.mycompany.authorbookapi.model.BookReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookResolver implements GraphQLResolver<Book> {

    private final AuthorService authorService;
    private final BookReviewApiClient bookReviewApiClient;
    private final BookReviewApiQueryBuilder bookReviewApiQueryBuilder;

    public Author getAuthor(Book book) {
        return authorService.validateAndGetAuthorById(book.getAuthor().getId());
    }

    public BookReview getBookReview(Book book) {
        String graphQLQuery = bookReviewApiQueryBuilder.getBookReviewQuery(book.getIsbn());
        BookReviewApiResult bookReviewApiResult = bookReviewApiClient.getBookReviews(graphQLQuery);
        return new BookReview(bookReviewApiResult);
    }
}
