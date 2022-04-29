package com.exam.domain.controller;

import com.exam.domain.common.ShortLinkCommon;
import com.exam.domain.common.ShortLinkUtils;
import com.google.common.hash.Hashing;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("short")
@Api("域名长短转化控制类")
public class ShortController {

    @ApiOperation("通过长域名转化短域名")
    @ApiImplicitParam(name = "url",value = "长域名地址",dataType = "String")
    @PostMapping("create")
    public String create(@PathVariable("url")String url) {
        long num = Hashing.murmur3_32().hashUnencodedChars(url).padToLong();
        String shortLinkCode = ShortLinkUtils.base62Encode(num);
        ShortLinkCommon.shortLinkMap.put(shortLinkCode, url);
        return ShortLinkCommon.DOMAIN_PREFIX.concat("/a").concat("/").concat(shortLinkCode);
    }

    @ApiOperation("通过短域名获取长域名")
    @ApiImplicitParam(name = "shorturl",value = "短域名地址",dataType = "String")
    @PostMapping("get")
    public String findurl(@PathVariable("shorturl") String shorturl){
        String fullLink = ShortLinkCommon.shortLinkMap.get(shorturl);
        return fullLink;
    }
}
