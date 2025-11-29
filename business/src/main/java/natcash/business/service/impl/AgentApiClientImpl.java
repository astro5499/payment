package natcash.business.service.impl;

import lombok.RequiredArgsConstructor;
import natcash.business.dto.response.PaymentHistoryResponse;
import natcash.business.dto.response.PaymentLogResponseDTO;
import natcash.business.service.AgentApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AgentApiClientImpl implements AgentApiClient {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String URL_FORMAT = "%s?fromDate=%s&toDate=%s&phone=%s&username=%s&password=%s";

    private final RestTemplate restTemplate;

    @Value("${natcash.agent.api.url}")
    private String natCashBaseUrl;

    @Value("${natcash.agent.api.user}")
    private String username;

    @Value("${natcash.agent.api.password}")
    private String password;

    @Override
    public List<PaymentLogResponseDTO> getPayments(String phoneNumber) {
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minusDays(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = String.format(URL_FORMAT,natCashBaseUrl, formatDate(yesterday), formatDate(currentDate), phoneNumber, username, password);
        URI uri = URI.create(url);

        ResponseEntity<PaymentHistoryResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, PaymentHistoryResponse.class);

        if (response.getBody() != null && "200".equals(response.getBody().getResultCode())) {
            return response.getBody().getResults();
        } else {
            return Collections.emptyList();
        }
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return date.format(formatter);
    }
}
