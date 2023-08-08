package com.meongmory.meongmory.domain.family.controller;

import com.meongmory.meongmory.domain.family.dto.request.CreateFamilyReq;
import com.meongmory.meongmory.domain.family.dto.response.AnimalTypeListRes;
import com.meongmory.meongmory.domain.family.dto.response.FamilyInviteCodeRes;
import com.meongmory.meongmory.domain.family.service.FamilyService;
import com.meongmory.meongmory.global.response.ResponseCustom;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "FAMILY API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/families")
public class FamilyController {
    private final FamilyService familyService;

    @PostMapping("")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001)다이어리 생성 성공", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "400", description = "(G0004)잘못된 요청\n (F0003)사용자당 가족 생성은 최대 1개", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0001)존재하지 않는 유저", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @Operation(summary = "펫 다이어리 생성", description = "이름을 입력받아 펫 다이어리(가족)을 생성합니다.")
    public ResponseCustom createFamily(@RequestBody @Valid CreateFamilyReq createFamilyReq){
        familyService.createFamily(createFamilyReq, 1L);
        return ResponseCustom.OK();
    }

    @GetMapping("/pet/kind")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001)반려동물 품종 검색 성공", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "400", description = "(A0001)반려동물 종류 이름 오류", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Operation(summary = "반려동물 품종 검색", description = "검색어와 반려동물 종류를 받아 품종을 검색합니다.")
    public ResponseCustom<AnimalTypeListRes> getAnimalType(
            @Parameter(description = "반려동물 품종 검색어", example = "말티즈") @RequestParam(required = false) String searchword,
            @Parameter(description = "반려동물 종류", example = "강아지") @RequestParam(required = false) String type,
            @Parameter Pageable pageable){
        return ResponseCustom.OK(familyService.getAnimalType(searchword, type, pageable));
    }

    @GetMapping("/{familyId}/invite")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001)펫 다이어리(가족) 초대 코드 조회 성공", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "400", description = "(F0004)펫 다이어리 생성 유저만 접근 가능", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0001)존재하지 않는 유저\n (F0001)존재하지 않은 다이어리(가족)\n (F0002)존재하지 않은 가족 구성원", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @Operation(summary = "펫 다이어리 초대 코드 조회", description = "펫 다이어리(가족)을 초대할 초대 코드 정보를 확인합니다.")
    public ResponseCustom<FamilyInviteCodeRes> getFamilyInviteCode(@Parameter(description = "(Long) 펫 다이어리(가족) Id", example = "1") @PathVariable(value = "familyId") Long familyId){
        return ResponseCustom.OK(familyService.getFamilyInviteCode(familyId, 1L));
    }
}
