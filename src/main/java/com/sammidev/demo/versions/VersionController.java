package com.sammidev.demo.versions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @GetMapping("/v1/siswa")
    public SiswaV1 siswaV1() {
        return new SiswaV1("Sammidev");
    }
    @GetMapping("/v2/siswa")
    public SiswaV2 siswaV2() {
        return new SiswaV2(new Name("sammi","Dev"));
    }

    @GetMapping(value = "/siswa/param", params = "version-1")
    public SiswaV1 paramV1() {
        return new SiswaV1("Sammidev");
    }
    @GetMapping(value = "/v2/param", params = "versio-2")
    public SiswaV2 paramV2() {
        return new SiswaV2(new Name("sammi","Dev"));
    }

    @GetMapping(value = "/siswa/header", headers = "X-API-VERSION=1")
    public SiswaV1 headerV1() {
        return new SiswaV1("Sammidev");
    }
    @GetMapping(value = "/siswa/header", headers = "X-API-VERSION=2")
    public SiswaV2 headerV2() {
        return new SiswaV2(new Name("sammi","Dev"));
    }

    @GetMapping(value = "/siswa/produces", produces = "application/vnd.company.app-v1+json")
    public SiswaV1 producesV1() {
        return new SiswaV1("sam");
    }

    @GetMapping(value = "/siswa/produces", produces = "application/vnd.company.app-v2+json")
    public SiswaV2 producesV2() {
        return new SiswaV2(new Name("Sammi", "dev"));
    }
}