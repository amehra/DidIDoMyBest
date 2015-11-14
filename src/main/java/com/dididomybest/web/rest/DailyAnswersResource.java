package com.dididomybest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dididomybest.domain.DailyAnswers;
import com.dididomybest.repository.DailyAnswersRepository;
import com.dididomybest.web.rest.util.HeaderUtil;
import com.dididomybest.web.rest.util.PaginationUtil;
import com.dididomybest.web.rest.dto.DailyAnswersDTO;
import com.dididomybest.web.rest.mapper.DailyAnswersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing DailyAnswers.
 */
@RestController
@RequestMapping("/api")
public class DailyAnswersResource {

    private final Logger log = LoggerFactory.getLogger(DailyAnswersResource.class);

    @Inject
    private DailyAnswersRepository dailyAnswersRepository;

    @Inject
    private DailyAnswersMapper dailyAnswersMapper;

    /**
     * POST  /dailyAnswerss -> Create a new dailyAnswers.
     */
    @RequestMapping(value = "/dailyAnswerss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DailyAnswersDTO> createDailyAnswers(@Valid @RequestBody DailyAnswersDTO dailyAnswersDTO) throws URISyntaxException {
        log.debug("REST request to save DailyAnswers : {}", dailyAnswersDTO);
        if (dailyAnswersDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dailyAnswers cannot already have an ID").body(null);
        }
        DailyAnswers dailyAnswers = dailyAnswersMapper.dailyAnswersDTOToDailyAnswers(dailyAnswersDTO);
        DailyAnswers result = dailyAnswersRepository.save(dailyAnswers);
        return ResponseEntity.created(new URI("/api/dailyAnswerss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dailyAnswers", result.getId().toString()))
            .body(dailyAnswersMapper.dailyAnswersToDailyAnswersDTO(result));
    }

    /**
     * PUT  /dailyAnswerss -> Updates an existing dailyAnswers.
     */
    @RequestMapping(value = "/dailyAnswerss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DailyAnswersDTO> updateDailyAnswers(@Valid @RequestBody DailyAnswersDTO dailyAnswersDTO) throws URISyntaxException {
        log.debug("REST request to update DailyAnswers : {}", dailyAnswersDTO);
        if (dailyAnswersDTO.getId() == null) {
            return createDailyAnswers(dailyAnswersDTO);
        }
        DailyAnswers dailyAnswers = dailyAnswersMapper.dailyAnswersDTOToDailyAnswers(dailyAnswersDTO);
        DailyAnswers result = dailyAnswersRepository.save(dailyAnswers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dailyAnswers", dailyAnswersDTO.getId().toString()))
            .body(dailyAnswersMapper.dailyAnswersToDailyAnswersDTO(result));
    }

    /**
     * GET  /dailyAnswerss -> get all the dailyAnswerss.
     */
    @RequestMapping(value = "/dailyAnswerss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DailyAnswersDTO>> getAllDailyAnswerss(Pageable pageable)
        throws URISyntaxException {
        Page<DailyAnswers> page = dailyAnswersRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dailyAnswerss");
        return new ResponseEntity<>(page.getContent().stream()
            .map(dailyAnswersMapper::dailyAnswersToDailyAnswersDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /dailyAnswerss/:id -> get the "id" dailyAnswers.
     */
    @RequestMapping(value = "/dailyAnswerss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DailyAnswersDTO> getDailyAnswers(@PathVariable Long id) {
        log.debug("REST request to get DailyAnswers : {}", id);
        return Optional.ofNullable(dailyAnswersRepository.findOne(id))
            .map(dailyAnswersMapper::dailyAnswersToDailyAnswersDTO)
            .map(dailyAnswersDTO -> new ResponseEntity<>(
                dailyAnswersDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dailyAnswerss/:id -> delete the "id" dailyAnswers.
     */
    @RequestMapping(value = "/dailyAnswerss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDailyAnswers(@PathVariable Long id) {
        log.debug("REST request to delete DailyAnswers : {}", id);
        dailyAnswersRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dailyAnswers", id.toString())).build();
    }
}
